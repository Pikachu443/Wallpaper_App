const rcedit = require('rcedit')


async function main() {
  // Simulate an asynchronous operation (e.g., fetching data)
  await rcedit("./percy_2.exe", {
    icon: "./myIcon.ico",
    "file-version": "1.0.0"
  })
}

main()
  .then(() => console.log("Script executed successfully"));
