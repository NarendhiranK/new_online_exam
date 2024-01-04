import React from "react";
import { useNavigate } from "react-router-dom";
import useStateRef from "react-usestateref";
//import SuccessPopUps from "../pop-ups/SuccessPopUps";
const Register = () => {
    var popTitle;
    const navigate = useNavigate();
    const [hasError, setHasError, hasNoError] = useStateRef(false);



    const SubmittingForm = (e) => {
        e.preventDefault();
         console.log("Submitting form called...");
         const myform=document.getElementById('myform');
         const formdata = new FormData(myform);
         console.log(formdata);
         setHasError(false);
         console.log('email field id...:',document.getElementById('emailFieldId').value);
         document.getElementById("p1").classList.add("d-none");
         document.getElementById("p2").classList.add("d-none");
         document.getElementById("p3").classList.add("d-none");
         document.getElementById("p4").classList.add("d-none");
         document.getElementById("p5").classList.add("d-none");
         // let username = data.get("username");
         // let password = data.get("password");
         const myobject = Object.fromEntries(formdata.entries());
         console.log('formdata:::::::::::::  ',myobject)
         Object.entries(myobject).map(([key, value], keyIndex) => {
           validateformlogin(key, value);
         });
         if(!hasNoError.current){
            fetch('https://localhost:8443/onlineexam/control/onUserRegistration', {
                method: 'POST',
                credentials:"include",
                headers: {
                    'Content-type': 'application/json',
                },
                body: JSON.stringify(Object.fromEntries(formdata.entries())),
            }).
                then(response => response.json())
                .then(data => {
                    console.log("data", data);
                    if (data.result2.responseMessage === "success") {
                
                        navigate("/");
                    }
                   
                })
                .catch((error) => console.log(error));
         }
       };
     const matchPassword =()=>{
         var pw1 =document.getElementById('passwordId');
         var pw2 = document.getElementById('confirmpasswordId');
         console.log('pw1 value : ',pw1 ,'pw2 value : ' ,pw2)
         if(pw1.value!=pw2.value){
             document.getElementById('p5').classList.remove('d-none');
             document.getElementById('p5').classList.add('d-block');
             document.getElementById("p5").innerHTML=
               "invalid! confirmpassword and password are not same ...";
               setHasError(true);
         }
     }
     const validateformlogin = (key, value) => {
       const firstNameId=document.getElementById('firstNameId');
         switch (key) {
           case "firstName":
             if (value == "" || value == null) {
               document.getElementById("p1").classList.remove("d-none");
               document.getElementById("p1").classList.add("d-block");
               document.getElementById("p1").innerHTML = "Please enter a Firstname";
               setHasError(false);
             } else {
               let regexx = new RegExp("^[A-Za-z]");
               if (!regexx.test(value)) {
                 document.getElementById('p1').classList.remove('d-none');
                 document.getElementById('p1').classList.add('d-block');
                 document.getElementById("p1").innerHTML=
                   "invalid! FirstName should be A-z a-z atleast 7 character and not be numbers...";
                 setHasError(true);
                 firstNameId.classList.add("was-validated");
               }
             }
             break;
           // case "Lastname":
           //   if (value == "" || value == null) {
           //     document.getElementById("p2").classList.remove("d-none");
           //     document.getElementById("p2").classList.add("d-block");
           //     document.getElementById("p2").innerHTML = "Please enter a Lastname";
           //     setHasError(true);
           //   }else {
           //       let regexx = new RegExp("^[A-Za-z][A-Za-z0-9_]{7,29}$");
           //       if (!regexx.test(value)) {
           //           document.getElementById('p2').classList.remove('d-none');
           //       document.getElementById('p2').classList.add('d-block');
           //         document.getElementById("p2").innerHTML=
           //           " invalid! LastName should conatins A-z a-z &0-9 atleast 7 character...";
           //         setHasError(true);
           //       }
           //     }
           //   break;
             case "userLoginId":
                 if (value == "" || value == null) {
                   document.getElementById("p3").classList.remove("d-none");
                   document.getElementById("p3").classList.add("d-block");
                   document.getElementById("p3").innerHTML = "Please enter emailaddress";
                   setHasError(true);
                 }else {
                     let regexx = new RegExp("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]");
                     if (!regexx.test(value)) {
                         document.getElementById('p3').classList.remove('d-none');
                 document.getElementById('p3').classList.add('d-block');
                       document.getElementById("p3").innerHTML=
                         " invalid! emailaddress should contains A-z a-z &0-9@a-z...";
                       setHasError(true);
                     }
                   }
                   break;
                   case "currentPassword":
                     if (value == "" || value == null) {
                       document.getElementById("p4").classList.remove("d-none");
                       document.getElementById("p4").classList.add("d-block");
                       document.getElementById("p4").innerHTML = "Please enter your password";
                       setHasError(true);
                     }else {
                            let regexx = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
                            console.log("password regex match : ",regexx.test(value));
                         if (!regexx.test(value)) {
                             document.getElementById('p4').classList.remove('d-none');
                            document.getElementById('p4').classList.add('d-block');
                           document.getElementById("p4").innerHTML=
                             "invalid! password should contains minimum 8 character...";
                           setHasError(true);
                         }
                       }
                       break;
                       case "currentPasswordVerify":
                         if (value == "" || value == null) {
                           document.getElementById("p5").classList.remove("d-none");
                           document.getElementById("p5").classList.add("d-block");
                           document.getElementById("p5").innerHTML = "Please enter confirm password";
                           setHasError(true);
                         }else {
                             matchPassword();
                           }
                           break;
           default: {
             // console.log("hii");
             setHasError(false);
           }
         }
        }


    return (
        <div className="row">
            <div className="col-4"></div>
            <div className="col-4 mt-5">
                <form onSubmit={SubmittingForm} id="myform">
                    <legend >Create Your Account Here</legend>
                    <div class="col">
                        <div data-mdb-input-init class="form-outline">
                            <label class="form-label float-start" for="form3Example1">
                                First name
                            </label>
                            <input type="text" name="firstName" id="firstNameId" placeholder="Enter your firstname" class="form-control" />
                         
                        </div>
                        <p id="p1" className="text-danger"></p>
                    </div>
                    <div class="col">
                        <div data-mdb-input-init class="form-outline">
                            <label class="form-label float-start" for="form3Example2">
                                Last name
                            </label>
                            <input type="text" name="lastName" id="form3Example2" placeholder="Enter your lastname" class="form-control" />
                        </div>
                        <p id="p2" className="text-danger"></p>
                    </div>
                    {/* <!-- Email input --> */}
                    <div data-mdb-input-init class="form-outline mb-4">
                        <label class="form-label float-start" for="form3Example3">
                            Email address
                        </label>
                        <input type="email" name="userLoginId" id="emailFieldId" class="form-control" placeholder="Enter your emailId"/>
                        <p id="p3" className="text-danger"></p>
                    </div>
                    {/* <!-- Password input --> */}
                    <div data-mdb-input-init class="form-outline mb-4">
                        <label class="form-label float-start" for="form3Example4">
                            Password
                        </label>
                        <input type="password" name="currentPassword" id="passwordId" class="form-control" placeholder="Enter your password" />

                        <p id="p4" className="text-danger"></p>
                                            </div>
                    <div data-mdb-input-init class="form-outline mb-4">
                        <label class="form-label float-start" for="form3Example4">
                            Confirm Password
                        </label>
                        <input type="password" name="currentPasswordVerify" id="confirmpasswordId" class="form-control" placeholder="Enter your confirm password" />
                        <p id="p5" className="text-danger"></p>
                    </div>
                    {/* <!-- Submit button --> */}
                    <button
                        data-mdb-ripple-init
                        type="submit"
                        class="btn btn-primary btn-block mb-4"
                    >
                        Sign up
                    </button>
                    {/* <SuccessPopUps title="{popTitle}" /> */}
                </form>
            </div>
            <div className="col-4"></div>
        </div>
    );
};
export default Register;


