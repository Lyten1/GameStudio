@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  project-ellen startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and PROJECT_ELLEN_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\project-ellen-1.0.jar;%APP_HOME%\lib\gamelib-framework-2.6.0.jar;%APP_HOME%\lib\gamelib-backend-lwjgl2-2.6.0.jar;%APP_HOME%\lib\gamelib-inspector-2.6.0.jar;%APP_HOME%\lib\gamelib-core-2.6.0.jar;%APP_HOME%\lib\inspector-1.0.3.jar;%APP_HOME%\lib\inspector-annotations-1.0.3.jar;%APP_HOME%\lib\gdx-backend-lwjgl-1.9.11.jar;%APP_HOME%\lib\gdx-freetype-1.9.11.jar;%APP_HOME%\lib\gdx-backend-headless-1.9.11.jar;%APP_HOME%\lib\gdx-1.9.11.jar;%APP_HOME%\lib\fast-classpath-scanner-3.1.9.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.4.10.jar;%APP_HOME%\lib\gdx-platform-1.9.11-natives-desktop.jar;%APP_HOME%\lib\gdx-freetype-platform-1.9.11-natives-desktop.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.4.10.jar;%APP_HOME%\lib\annotations-17.0.0.jar;%APP_HOME%\lib\lwjgl_util-2.9.3.jar;%APP_HOME%\lib\lwjgl-2.9.3.jar;%APP_HOME%\lib\jlayer-1.0.1-gdx.jar;%APP_HOME%\lib\jorbis-0.0.17.jar;%APP_HOME%\lib\qdox-2.0-M2.jar;%APP_HOME%\lib\swingx-all-1.6.4.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-windows.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-linux.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-osx.jar;%APP_HOME%\lib\jinput-2.0.5.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.4.10.jar;%APP_HOME%\lib\jutils-1.0.0.jar;%APP_HOME%\lib\jinput-platform-2.0.5-natives-linux.jar;%APP_HOME%\lib\jinput-platform-2.0.5-natives-windows.jar;%APP_HOME%\lib\jinput-platform-2.0.5-natives-osx.jar


@rem Execute project-ellen
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PROJECT_ELLEN_OPTS%  -classpath "%CLASSPATH%" sk.tuke.kpi.oop.game.Main %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable PROJECT_ELLEN_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%PROJECT_ELLEN_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
