7z x node-v14.20.0.tar.gz
7z x node-v14.20.0.tar
@REM copy ..\..\..\bslocal.js node-latest\lib
pushd node-v14.20.0.tar
vcbuild.bat without-intl ia32