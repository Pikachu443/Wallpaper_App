var config = {
    apiKey: "AIzaSyAazMmEFViisc7pTZIRJvceGXlZtLAzsdw",
    authDomain: "wallpaperapp-ad374.firebaseapp.com",
    databaseURL: "https://wallpaperapp-ad374.firebaseio.com",
    projectId: "wallpaperapp-ad374",
    storageBucket: "wallpaperapp-ad374.appspot.com",
    messagingSenderId: "338821714477"
  };
  firebase.initializeApp(config);


    firebase.auth.Auth.Persistence.LOCAL; 

$("#btn-login").click(function(){
    
    var email = $("#email").val();
    var password = $("#password").val();


    var result = firebase.auth().signInWithEmailAndPassword(email,password);

    result.catch(function(error){
        var errcode = error.code;
        var errmsg = error.message;

        console.log(errcode);
        console.log(errmsg);
        

    });
});
$("#logout").click(function(){
    firebase.auth().signOut();
});

function switchView(view){

    $.get({
        url:view,
        cache: false,

    }).then(function(data){
        $("#container").html(data)
    })
}

