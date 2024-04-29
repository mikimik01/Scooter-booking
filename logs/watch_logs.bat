@ECHO OFF
:loop
  cls
  type logs.txt
  timeout /t 1 > NUL
goto loop