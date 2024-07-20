document.addEventListener('DOMContentLoaded', function(){
    const title = document.getElementById('title');
    const login = document.getElementById('login');
    const signup = document.getElementById('signup');
    title.addEventListener('click', function(){
        window.location.href = "https://naver.com";
    });
    login.addEventListener('click', function(){
        window.location.href = "/login";
    });
    signup.addEventListener('click', function(){
        window.location.href = "/signup";
    });
});