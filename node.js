const rcedit = require('rcedit')

await rcedit("./percy.exe", {
  icon: "./myIcon.ico",
  "file-version": "1.0.0"
})