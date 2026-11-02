@echo off
setlocal
cd /d "%~dp0"

echo ================================================
echo TIAB GTM Compat - DeepSpace GitHub sync
echo ================================================

where git >nul 2>&1
if errorlevel 1 (
  echo ERROR: Git was not found in PATH.
  exit /b 1
)

git switch main
if errorlevel 1 exit /b 1

git add -A
if errorlevel 1 exit /b 1

rem ModularUI is a local compileOnly dependency and is ignored by .gitignore.
if exist "libs\modularui-*.jar" git add -f libs/modularui-*.jar
if errorlevel 1 exit /b 1

git diff --cached --quiet
if errorlevel 1 (
  git commit -m "Sync TIAB GTM Compat Sky of Grind DeepSpace changes"
  if errorlevel 1 exit /b 1
) else (
  echo No local changes to commit.
)

git push -u deepspace main
if errorlevel 1 (
  echo ERROR: Push failed. Local commits are preserved and can be pushed again.
  exit /b 1
)

echo.
echo Successfully synced to https://github.com/evilcatshb/tiab-gtm-compat-deepspace-fork
exit /b 0
