import "bootstrap/dist/css/bootstrap.css";
import useStateRef from "react-usestateref";
import React from "react";
import { useNavigate,Link } from "react-router-dom";

const Login = ({setName,setFlag,flag}) => {
  const navigate = useNavigate();
  const [hasError, setHasError, hasNoError] = useStateRef(false);

  const SubmittingForm = (e) => {
    e.preventDefault();
    console.log("Submitting form called...");
    const formdata = new FormData(e.target);
    setHasError(false);
    document.getElementById("p1").classList.add("d-none");
    document.getElementById("p2").classList.add("d-none");

    let username = formdata.get("USERNAME");
    let password = formdata.get("PASSWORD");

    console.log("username.....", username);
    console.log("password......", password);
    const myobject = Object.fromEntries(formdata.entries());
    Object.entries(myobject).map(([key, value], keyIndex) => {
      validateFormLogin(key, value);
    });

    if (!hasNoError.current) {
      fetch('https://localhost:8443/onlineexam/control/loginEvent', {
        method: 'POST',
        credentials: "include",
        headers: {
          'Content-type': 'application/json',
        },
        body: JSON.stringify(myobject),
      }).
        then(response => response.json())
        .then(data => {
          console.log("data", data);
          //console.log(data.resultMap.checkGenericValue.roleTypeId);
          //console.log(data.resultMap.checkGenericValue.partyId);
          // console.log(data.resultMap);
      
          if (data.resultMap.usersRoletypeId === "ADMIN") {
            console.log("isFlag", data.resultMap.isFlag);
            //alert("admin logged in successfully...")
            setName(data.resultMap.firstName);
            setFlag(data.resultMap.isFlag);
          
            navigate("admin/welcome");
          }
          else if (data.resultMap.usersRoletypeId === "PERSON_ROLE") {
            setName(data.resultMap.firstName);
            setFlag(data.resultMap.isFlag);
          
            navigate("/user")
          }
          else if (data.resultMap.login_condition === "error") {
            setFlag(data.resultMap.isFlag);
            document.getElementById('div1').classList.remove('d-none');
            document.getElementById('div1').classList.add('d-block');
            navigate("/");
          }
        })
        .catch((error) => console.log(error));
        };
    }


  const validateFormLogin = (key, value) => {
    switch (key) {
      case "USERNAME":
        if (value === "" || value === null) {
          document.getElementById("p1").classList.remove("d-none");
          document.getElementById("p1").classList.add("d-block");
          document.getElementById("p1").innerHTML = "Please enter a username";
          setHasError(true);
        }
        else {
          let regexx = new RegExp("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]");
          if (!regexx.test(value)) {
              document.getElementById('p1').classList.remove('d-none');
      document.getElementById('p1').classList.add('d-block');
            document.getElementById("p1").innerHTML=
              " invalid! emailaddress should contains A-z a-z &0-9@a-z...";
            setHasError(true);
          }
        }
        break;
      case "PASSWORD":
        if (value === "" || value == null) {
          document.getElementById("p2").classList.remove("d-none");
          document.getElementById("p2").classList.add("d-block");
          document.getElementById("p2").innerHTML = "Please enter a password";
          setHasError(true);
        }

        break;

      default: {
        setHasError(false);
        
      }
    }
  };

  return (
    <div className="row my-5 ">
      <div className="col-4"></div>
      <div className="col-4 shadow">
      <p id="div1" className="d-none text-danger">please Check your username and password</p>
        <form onSubmit={SubmittingForm}>
          <div className="form-group">
            <label
              htmlFor="exampleInputEmail1"
              className="form-label float-start lead"
            >
              Username:
            </label>
            <input
              type="text"
              className="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              placeholder="Enter your username"
              name="USERNAME"
            />
            <p id="p1" className="d-none text-danger"></p>
          </div>
          <div className="form-group mt-4">
            <label
              htmlFor="exampleInputPassword1"
              className="form-label float-start lead"
            >
              Password:
            </label>
            <input
              type="password"
              className="form-control "
              id="exampleInputPassword1"
              placeholder="Password"
              name="PASSWORD"
            />
            <p id="p2" className="d-none text-danger mt-2"></p>
            <p id="p3" className="d-block text-dark mt-2">Don't have an account?</p>

            <Link to="register" className="text-decoration-none"> Register Here</Link>
          </div>

          <button type="submit" className="btn btn-primary my-3">
            login
          </button>
        </form>
      </div>
      <div className="col-4"></div>
    </div>
  );
};

export default Login;
