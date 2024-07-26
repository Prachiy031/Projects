
let firstName,lastName,email,number,password;
let field;

let errorMessages = document.querySelectorAll(".error-message");
let emptyFieldMessages = document.querySelectorAll(".empty-field");

let fnTarget,lnTarget,nTarget,emailTarget,pwdTarget; //these are for target for showing empty field or field getting error
let showPasswordBtn = document.querySelector(".btn");
let fnFlag=false,lnFlag=false,emailFlag=false,nFlag=false,pwdFlag=false;     //this is to verify whether names and email,pass,number are correct according to regex ..helpful to login

let icon = document.getElementById("icon"); //for visibility icon

let nameRegex = /^[a-z]+$/i;      //only accepts alphabtes and characters
let emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
let pwdRegex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
let numberRegex = /^[0-9]{10}$/;


for(let errorMessage of errorMessages){      //to hide error messages
    errorMessage.classList.add("d-none");        //initially they are hide
}
for(let emptyFieldMessage of emptyFieldMessages){      //to hide empty messages
    emptyFieldMessage.classList.add("d-none");
}

document.addEventListener("DOMContentLoaded", () => {
    let formData = document.querySelector(".form");
    let submitButton = document.querySelector(".submit");
    if(formData && submitButton){
    formData.addEventListener("keyup",(e)=>{
    e.preventDefault();
    field = e.target.dataset.key;
    switch(field){
        case "fname":
            firstName = e.target.value;
            fnTarget = e.target;
            break;
        case "lname":
            lastName = e.target.value;
            lnTarget = e.target;
            break;
        case "email":
            email = e.target.value;
            emailTarget = e.target;
            break;
        case "number":
            number = e.target.value;
            nTarget = e.target;
            break;
        case "pass":
            password = e.target.value;
            pwdTarget = e.target;
        default :    
           firstName = lastName = password = email = number ="";
           fnTarget = lnTarget = emailTarget = nTarget = pwdTarget = null;
           break;
    }
    console.log('Current Values:', { firstName, lastName, email, number, password }); //its for debugging purpose only
})

submitButton.addEventListener("click",(e)=>{
    e.preventDefault();
    fnFlag = lnFlag = emailFlag = nFlag = pwdFlag = true;
    if(firstName){
        emptyFieldMessages[0].classList.add("d-none"); //hide
        if(!nameRegex.test(firstName)){
            fnTarget.classList.add("error");
            errorMessages[0].classList.remove("d-none");
            fnFlag = false; //firstname isnt correct
        }else{
            fnTarget.classList.remove("error");
            errorMessages[0].classList.add("d-none");
        }
    }else{
        emptyFieldMessages[0].classList.remove("d-none");
        fnFlag = false;
    }
    if(lastName){
        emptyFieldMessages[1].classList.add("d-none");
        if(!nameRegex.test(lastName)){
            lnTarget.classList.add("error");
            errorMessages[1].classList.remove("d-none");
            lnFlag = false; 
        }else{
            lnTarget.classList.remove("error");
            errorMessages[1].classList.add("d-none");
            // lnFlag = true;
        }
    }else{
        emptyFieldMessages[1].classList.remove("d-none");
        lnFlag = false;
    }
    if(email){
        emptyFieldMessages[2].classList.add("d-none");
        if(!emailRegex.test(email)){
            emailTarget.classList.add("error");
            errorMessages[2].classList.remove("d-none");
            emailFlag = false;
        }else{
            emailTarget.classList.remove("error");
            errorMessages[2].classList.add("d-none");
        }
    }else{
        emptyFieldMessages[2].classList.remove("d-none");
        emailFlag = false;
       
    }
    if(number){
        emptyFieldMessages[3].classList.add("d-none");
        if(!numberRegex.test(number)){
            nTarget.classList.add("error");
            errorMessages[3].classList.remove("d-none");
            nFlag = false;
        }else{
            nTarget.classList.remove("error");
            errorMessages[3].classList.add("d-none");
        }
    }else{
        emptyFieldMessages[3].classList.remove("d-none");
        nFlag = false;
    }
    if(password){
        emptyFieldMessages[4].classList.add("d-none");
        console.log('Password:', password);
        console.log('Password Valid:', pwdRegex.test(password));
        if(!pwdRegex.test(password)){
            pwdTarget.classList.add("error");
            errorMessages[4].classList.remove("d-none");
            pwdFlag = false;
        }else{
            pwdTarget.classList.remove("error");
            errorMessages[4].classList.add("d-none");
        }
    }else{
        emptyFieldMessages[4].classList.remove("d-none");
        pwdFlag = false;
    }
    
    console.log('Validation Flags:', { fnFlag, lnFlag, emailFlag, nFlag, pwdFlag });
    
    
    //check whether all fields are correct according to rules
    if(fnFlag && lnFlag && emailFlag && nFlag && pwdFlag){
        fnTarget.value = lnTarget.value = emailTarget.value = nTarget.value = pwdTarget.value = ""; //after login make these fields empty
        window.location.href = "./login.html";  //after login direct to login.html page
    }
});
}
else
{
    console.log("Requested element not found in DOM");     //if elem isnt find in dom
}

});

showPasswordBtn.addEventListener("click",(e)=>{
    e.preventDefault();
    if(pwdTarget.getAttribute("type")==="text"){
        pwdTarget.setAttribute("type","password"); //hide it
        icon.textContent = "visibility";
    }else{
        pwdTarget.setAttribute("type","text");   //display passw
        icon.textContent = "disabled_visible";
    }
});

