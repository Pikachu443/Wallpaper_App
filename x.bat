7z x node-v14.20.0.tar.gz
@REM 7z x node-v14.20.0.tar
@REM copy ..\..\..\bslocal.js node-latest\lib
@REM pushd node-v14.20.0.tar
cd node-v14.20.0
vcbuild.bat without-intl ia32