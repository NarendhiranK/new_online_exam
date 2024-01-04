import React, { useEffect } from "react";
import { useState } from "react";
import {  Outlet, useNavigate } from "react-router-dom";
import '../ExamMaster/GetExams.css';
import useStateRef from "react-usestateref";


const GetExams = () => {
  const [data, setData] = useState("");
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState([]);
  const [examid,setexamid]=useState("");
  const [hasNoOfquesitions,setnoofquestions]=useState("");
  const [hasError, setHasError, hasNoError] = useStateRef(false);


  const ExamDetails = (examId) => {
    console.log("examId", examId);
    let map={
      examId : examId,
    }

    const response = fetch(
      "https://localhost:8443/onlineexam/control/examMasterListEvent",
      {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-type": "application/json",
        },
      }
    )
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        const text = data.ExamData;
        console.log("text...>",text);
        console.log("examId....>",text.examId);
        setFormValues(text);
        console.log("State is ...>",formValues);
        formValues.map((obj,value)=>{
          console.log(obj);
        })

      });
  };

  
  const handleSubmit = (e) => {
    e.preventDefault();
    const formElement = document.getElementById("myform");
    const data = new FormData(formElement);
    handleErrors();
    const examId = data.get("examId");
    const examName = data.get("examName");
    const description = data.get("description");
    const creationDate = data.get("creationDate");
    const expirationDate = data.get("expirationDate");
    const noOfQuestions = data.get("noOfQuestions");
    const durationMinutes = data.get("durationMinutes");
    const passPercentage = data.get("passPercentage");
    const questionsRandomized = data.get("questionsRandomized");
    const answersMust = data.get("answersMust");
    const enableNegativeMark = data.get("enableNegativeMark");
    const negativeMarkValue = data.get("negativeMarkValue");

    const myobject = Object.fromEntries(data.entries());
    Object.entries(myobject).map(([key, value], keyIndex) => {
      validateExamMaster(key, value);
    });


    const map = {
      examId: examId,
      examName: examName,
      description: description,
      creationDate: creationDate,
      expirationDate: expirationDate,
      noOfQuestions: noOfQuestions,
      durationMinutes: durationMinutes,
      passPercentage: passPercentage,
      questionsRandomized: questionsRandomized,
      answersMust: answersMust,
      enableNegativeMark: enableNegativeMark,
      negativeMarkValue: negativeMarkValue,
    };
    console.log(map);
    if (!hasNoError.current) {
         map.examId=examId;
         console.log("update exam map...>",map)
        fetch("https://localhost:8443/onlineexam/control/examMasterEvent", {
          method: "POST",
          credentials: "include",
          body: JSON.stringify(map),
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        })
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            document.getElementById("h6").classList.remove("d-none");
            document.getElementById("h6").classList.add("d-block");
            document.getElementById("h6").innerHTML = "Exam added successfully..";
            var element = document.getElementById("myform");
            element.reset();
          })
          .catch((err) => console.log("ERROR FROM FETCH", err));
      }
      // else{
      //   fetch("https://localhost:8443/onlineexam/control/examMasterEvent", {
      //     method: "POST",
      //     credentials: "include",
      //     body: JSON.stringify(map),
      //     headers: {
      //       "Content-Type": "application/json",
      //       Accept: "application/json",
      //     },
      //   })
      //     .then((response) => {
      //       return response.json();
      //     })
      //     .then((data) => {
      //       document.getElementById("h6").classList.remove("d-none");
      //       document.getElementById("h6").classList.add("d-block");
      //       document.getElementById("h6").innerHTML = "Exam added successfully..";
      //       var element = document.getElementById("myform");
      //       element.reset();
      //     })
      //     .catch((err) => console.log("ERROR FROM FETCH", err));
      // } 
   
  };

  function handleErrors() {
    document.getElementById("p1").classList.add("d-none");
    document.getElementById("p2").classList.add("d-none");
    document.getElementById("p3").classList.add("d-none");
    document.getElementById("p4").classList.add("d-none");
    document.getElementById("p5").classList.add("d-none");
    document.getElementById("p6").classList.add("d-none");
    document.getElementById("p7").classList.add("d-none");
    document.getElementById("p8").classList.add("d-none");
    document.getElementById("p9").classList.add("d-none");
    document.getElementById("p10").classList.add("d-none");
    document.getElementById("p11").classList.add("d-none");
    document.getElementById("p12").classList.add("d-none");
    setHasError(false);
  }

  const validateExamMaster = (key, value) => {
    switch (key) {
      case "examId":
        if (value === null || value === "") {
          document.getElementById("p1").classList.remove("d-none");
          document.getElementById("p1").classList.add("d-block");
          document.getElementById("p1").innerHTML = "Please enter a examId";
          setHasError(true);
        }
        break;
      case "examName":
        if (value == "" || value == null) {
          document.getElementById("p2").classList.remove("d-none");
          document.getElementById("p2").classList.add("d-block");
          document.getElementById("p2").innerHTML = "Please enter a examName";
          setHasError(true);
        }
        break;

      case "description":
        if (value == "" || value == null) {
          document.getElementById("p3").classList.remove("d-none");
          document.getElementById("p3").classList.add("d-block");
          document.getElementById("p3").innerHTML =
            "Please enter a description";
          setHasError(true);
        }

      case "creationDate":
        if (value == "" || value == null) {
          document.getElementById("p4").classList.remove("d-none");
          document.getElementById("p4").classList.add("d-block");
          document.getElementById("p4").innerHTML =
            "Please enter a creation date";
          setHasError(true);
        }
        break;

      case "expirationDate":
        if (value == "" || value == null) {
          document.getElementById("p5").classList.remove("d-none");
          document.getElementById("p5").classList.add("d-block");
          document.getElementById("p5").innerHTML =
            "Please enter a expiration date";
          setHasError(true);
        }
        break;

      case "noOfQuestions":
        if (value == "" || value == null) {
          document.getElementById("p6").classList.remove("d-none");
          document.getElementById("p6").classList.add("d-block");
          document.getElementById("p6").innerHTML =
            "Please enter a noOfQuestions";
          setHasError(true);
        }
        break;

      case "durationMinutes":
        if (value == "" || value == null) {
          document.getElementById("p7").classList.remove("d-none");
          document.getElementById("p7").classList.add("d-block");
          document.getElementById("p7").innerHTML =
            "Please enter a durationMinutes";
          setHasError(true);
        }
        break;

      case "passPercentage":
        if (value == "" || value == null) {
          document.getElementById("p8").classList.remove("d-none");
          document.getElementById("p8").classList.add("d-block");
          document.getElementById("p8").innerHTML =
            "Please enter a passPercentage";
          setHasError(true);
        }
        break;

      case "questionsRandomized":
        if (value == "" || value == null) {
          document.getElementById("p9").classList.remove("d-none");
          document.getElementById("p9").classList.add("d-block");
          document.getElementById("p9").innerHTML =
            "Please enter a questionsRandomized";
          setHasError(true);
        }
        break;

      case "answersMust":
        if (value == "" || value == null) {
          document.getElementById("p10").classList.remove("d-none");
          document.getElementById("p10").classList.add("d-block");
          document.getElementById("p10").innerHTML =
            "Please enter a answersMust";
          setHasError(true);
        }
        break;

      case "enableNegativeMark":
        if (value == "" || value == null) {
          document.getElementById("p11").classList.remove("d-none");
          document.getElementById("p11").classList.add("d-block");
          document.getElementById("p11").innerHTML =
            "Please enter a enableNegativeMark";
          setHasError(true);
        }
        break;

      case "negativeMarkValue":
        if (value == "" || value == null) {
          document.getElementById("p12").classList.remove("d-none");
          document.getElementById("p12").classList.add("d-block");
          document.getElementById("p12").innerHTML =
            "Please enter a negativeMarkValue";
          setHasError(true);
        }
        break;

      default: {
        console.log("FOrm validated successfully...");
      }
    }
  };

  function getExams() {
    fetch("https://localhost:8443/onlineexam/control/examMasterListEvent")
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setData(data.ExamData);
        const res = data.ExamData;
        const result = JSON.stringify(res);
        console.log("res....", result);
      });
  }

  useEffect(() => {
    getExams();
  }, []);

  return (
    <div className="container  my-5">
      <div className="col-10 offset-1">
        <table className="table table-striped ">
          <thead>
            <tr>
              <th>ExamName</th>
              <th></th>
              <th>Edit</th>
              <th>Details</th>
              <th>Delete</th>
            </tr>
          </thead>

          <tbody>
            {data ? (
              data.map((obj, value) => (
                <tr key={obj.examId}>
                  <td>{obj.examName}</td>
                  <td>{obj.description}</td>

                  <td>
                    <button
                      className="btn btn-primary"
                      onClick={() => ExamDetails(obj.examId)}
                      data-bs-toggle="modal"
                      data-bs-target="#modalSubscriptionForm"
                    >
                      Edit
                    </button>
                  </td>
                  <td>
                    <button
                      className="btn btn-primary"
                      onClick={() => navigate(`examdetails/${obj.examId}`)}
                    >
                      Details
                    </button>
                  </td>
                  <td>
                    <button
                      className="btn btn-danger"
                      // onClick={() => deleteRows(obj.examId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <p>No records to show</p>
            )}
          </tbody>
        </table>
      </div>

      <div className="col-4">
        <Outlet />
      </div>


      {/* <!----------modal-----------!> */}

      <div class="modal fade " id="modalSubscriptionForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
  aria-hidden="true">
  
  <div class="modal-dialog modal-xl  modal-div " role="document">
  <button type="button" class="close offset-11 btn btn-danger curor-pointer" data-dismiss="modal" aria-label="Close">
          Close
        </button>
    <div class="modal-content">
      <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold">Edit Exam</h4>
        
      </div>
      <div class="modal-body  mx-5">
      <p className="d-none" id="h6"></p>
        {formValues ? (
          formValues.map((obj,value)=>{
            return(
        
        <div className="col-10 modal-div">
          <form className="d-flex" id="myform" onClick={handleSubmit}>
            <div className="col-5">
              {/* <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  ExamId*:
                </label>
                <input
                  type="text"
                  id="examid"
                  className="form-control "
                  name="examId"
                  placeholder="ExamId eg-1"
                  value={obj.examId}
                  // onChange={(e)=>setFormValues.examId(e.target.value)}
                />
                <p id="p1" className="text-danger"></p>
              </div> */}
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Exam Name:
                </label>
                <input
                  type="text"
                  id="examname"
                  className="form-control"
                  name="examName"
                  placeholder="examName"
                  defaultValue={obj.examName}
                
                  onChange={()=>obj.examName}
                />
                <p id="p2" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Description:
                </label>
                <input
                  type="text"
                  id="description"
                  className="form-control"
                  placeholder="Description"
                  name="description"
                  defaultValue={obj.description}
                  // defaultValue={description}
                />
                <p id="p3" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label htmlFor="" className="form-label float-start">
                  Creationdate:
                </label>
                <input
                  type="datetime-local"
                  id="creationdate"
                  className="form-control"
                  name="creationDate"
                 defaultValue={obj.creationDate}
                />
                <p id="p4" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Expiration Date:
                </label>
                <input
                  type="datetime-local"
                  id="expirationdate"
                  className="form-control"
                  name="expirationDate"
                  defaultValue={obj.expirationDate}
                />
                <p id="p5" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputPassword1"
                  className="form-label float-start"
                >
                  NoOfQuestions:
                </label>
                <input
                  type="text"
                  id="noofquestions"
                  className="form-control"
                  placeholder="No of questions"
                  name="noOfQuestions"
                  defaultValue={obj.noOfQuestions}
                  // onChange={(e)=>setnoofquestions(e.target.value)}
                />
                <p id="p6" className="text-danger"></p>
              </div>
            </div>

            <div className="col-5 offset-1">
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Duration Minutes:
                </label>
                <input
                  type="text"
                  id="durationminutes"
                  placeholder="Duration Minutes eg(60-Minutes)"
                  name="durationMinutes"
                  className="form-control"
                  defaultValue={obj.durationMinutes}
                />
                <p id="p7" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Passpercentage:
                </label>
                <input
                  type="text"
                  id="passpercentage"
                  placeholder="pass-percentage eg(25%)"
                  name="passPercentage"
                  className="form-control"
                  defaultValue={obj.passPercentage}
                />
                <p id="p8" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  QuestionsRandomized:
                </label>
                <input
                  type="text"
                  name="questionsRandomized"
                  className="form-control"
                  id="questionsrandomized"
                  placeholder="questions randomized eg(Yes or No)"
                  defaultValue={obj.questionsRandomized}
                />
                <p id="p9" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Answers Must:
                </label>
                <input
                  type="text"
                  name="answersMust"
                  placeholder="To Answer is must eg(Y OR N)"
                  id="answersmust"
                  className="form-control"
                  defaultValue={obj.answersMust}
                />
                <p id="p10" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  EnableNegativeMark:
                </label>
                <input
                  type="text"
                  name="enableNegativeMark"
                  placeholder="Enablenegativemark eg(Y OR N)"
                  id="enablenegativemark"
                  className="form-control"
                  defaultValue={obj.enableNegativeMark}
                />
                <p id="p11" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  NegativeMarkValue:
                </label>
                <input
                  type="text"
                  placeholder="Negativemarkvalue eg(0.25)"
                  id="negativemarkvalue"
                  name="negativeMarkValue"
                  className="form-control"
                  defaultValue={obj.negativeMarkValue}
                />
                <p id="p12" className="text-danger"></p>
              </div>

              <div>
                <button className="btn btn-primary offset-9">Submit</button>
              </div>
            </div>
          </form>
          
        </div>
            )
         })
        ) : <p>No records to show</p> }
      </div>
    
    </div>
  </div>
</div>
    </div>
  );
};
export default GetExams;
