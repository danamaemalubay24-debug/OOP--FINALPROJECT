param([switch]$Test)

$ErrorActionPreference = 'Stop'

$jdkBin = @(
    if ($env:JAVA_HOME) {
        Join-Path $env:JAVA_HOME 'bin'
    }

    $pathCompiler = Get-Command 'javac.exe' -CommandType Application -ErrorAction SilentlyContinue
    if ($pathCompiler) {
        Split-Path -Path $pathCompiler.Source
    }

    $oracleJava = Join-Path $env:ProgramFiles 'Java'
    Get-ChildItem -LiteralPath $oracleJava -Directory -Filter 'jdk*' -ErrorAction SilentlyContinue |
        Sort-Object Name -Descending |
        ForEach-Object { Join-Path $_.FullName 'bin' }
) | Where-Object {
    (Test-Path -LiteralPath (Join-Path $_ 'javac.exe') -PathType Leaf) -and
    (Test-Path -LiteralPath (Join-Path $_ 'java.exe') -PathType Leaf)
} | Select-Object -First 1

if (-not $jdkBin) {
    throw 'A Java JDK was not found. Set JAVA_HOME to your JDK installation directory and try again.'
}

$sourceDirectory = Join-Path $PSScriptRoot 'src/main/java'
$classesDirectory = Join-Path $PSScriptRoot 'build/classes'
$sources = @(Get-ChildItem -LiteralPath $sourceDirectory -Recurse -Filter '*.java' -File |
    Select-Object -ExpandProperty FullName)
New-Item -ItemType Directory -Path $classesDirectory -Force | Out-Null

$compiler = Join-Path $jdkBin 'javac.exe'
& $compiler -encoding UTF-8 -d $classesDirectory @sources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

$runtime = Join-Path $jdkBin 'java.exe'
if ($Test) {
    $testSourceDirectory = Join-Path $PSScriptRoot 'src/test/java'
    $testClassesDirectory = Join-Path $PSScriptRoot 'build/test-classes'
    $testSources = @(Get-ChildItem -LiteralPath $testSourceDirectory -Recurse -Filter '*.java' -File |
        Select-Object -ExpandProperty FullName)
    New-Item -ItemType Directory -Path $testClassesDirectory -Force | Out-Null

    & $compiler -encoding UTF-8 -cp $classesDirectory -d $testClassesDirectory @testSources
    if ($LASTEXITCODE -ne 0) {
        exit $LASTEXITCODE
    }

    $classpath = @($classesDirectory, $testClassesDirectory) -join [IO.Path]::PathSeparator
    $casesDirectory = Join-Path $PSScriptRoot 'src/test/cases'
    & $runtime '-Dfile.encoding=UTF-8' -cp $classpath instrumentreservation.ConsoleTests $casesDirectory
    exit $LASTEXITCODE
}

& $runtime -cp $classesDirectory instrumentreservation.Main
exit $LASTEXITCODE
