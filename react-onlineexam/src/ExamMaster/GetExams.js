import React, { useEffect } from "react";
import { useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import "../ExamMaster/GetExams.css";
import useStateRef from "react-usestateref";
import { control, pluginName, port, protocol } from "../constants";

const GetExams = () => {
  const [data, setData] = useState("");
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    examName: "",
    description: "",
    creationDate: "",
    expirationDate: "",
    noOfQuestions: "",
    durationMinutes: "",
    passPercentage: "",
    questionsRandomized: "",
    answersMust: "",
    enableNegativeMark: "",
    negativeMarkValue: "",
  });

  const [formValues, setFormValues] = useState([]);
  const [examid, setexamid] = useState("");
  const [examname, setexamname, hasExamName] = useStateRef("");
  const [description, setdescription, hasDescription] = useStateRef("");
  const [creationdate, setcreationdate, hasCreatetionDate] = useStateRef("");
  const [expirationdate, setexpirationdate] = useState("");
  const [noofquestions, setnoofquestions] = useState("");
  const [durationminutes, setdurationminutes] = useState("");
  const [passpercentage, setpasspercentage] = useState("");
  const [questionsrandomized, setquestionsrandomized] = useState("");
  const [answersmust, setanswersmust] = useState("");
  const [enablenegativemark, setenablenegativemark] = useState("");
  const [negativemarkvalue, setnegativemarkvalue] = useState("");

  const [hasError, setHasError, hasNoError] = useStateRef(false);

  const ExamDetails = (examId) => {
    console.log("examId", examId);
    let map={
      examId : examId,
    }

    const response = fetch(
      protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/examMasterListEvent",
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
        console.log("text...>", text);
        console.log("examname...>", text.examName);
        console.log("examId....>", text.examId);
        setFormValues(text);
        console.log("State is ...>", formValues);
        text.map((obj, value) => {
          console.log("examname...>", examname);
          setexamid(obj.examId);
          setexamname(obj.examName);
          setdescription(obj.description);
          setcreationdate(obj.creationDate);
          setexpirationdate(obj.expirationDate);
          setnoofquestions(obj.noOfQuestions);
          setanswersmust(obj.answersMust);
          setdurationminutes(obj.durationMinutes);
          setpasspercentage(obj.passPercentage);
          setquestionsrandomized(obj.questionsRandomized);
          setenablenegativemark(obj.enableNegativeMark);
          setnegativemarkvalue(obj.negativeMarkValue);
        });
        // document.getElementById("modalId").click();
       
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
      examId: examid,
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
    console.log("handlew submit map....>",map);
    if (!hasNoError.current) {
         map.examId=examId;
         console.log("update exam map...>",map)
        fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/examMasterEvent", {
          method: "POST",
          credentials: "include",
          body: JSON.stringify(map),
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        })
        .then((data) => {
          if (data._EVENT_MESSAGE_) {
            document.getElementById("h6").classList.remove("d-none");
            document.getElementById("h6").classList.add("d-block");
            document.getElementById("h6").innerHTML =
              "Exam updated successfully..";
          }
          console.log("data",data);
          // window.location.reload();
          document.getElementById('butn1').click();
          var element = document.getElementById("myform");
          element.reset();
        })
        .catch((err) => console.log("ERROR FROM FETCH", err));
    }
  };

  function handleErrors() {
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
    document.getElementById('h6').classList.add('d-none');
    setHasError(false);
  }

  const validateExamMaster = (key, value) => {
    switch (key) {
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
        console.log(data);
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
                      // id="modalId"
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

      <div
        class="modal fade"
        id="modalSubscriptionForm"
        tabindex="-1"
        role="dialog"
        aria-labelledby="myModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-fullscreen-xxl-down  modal-div " role="document">
          <button
            type="button"
            class="close offset-11 btn btn-danger curor-pointer"
            data-dismiss="modal"
            aria-label="Close"
            id="butn1"
            // onClick={getExams()}
          >
            Close
          </button>
          <div class="modal-content">
            <div class="modal-header text-center">
              <h4 class="modal-title w-100 font-weight-bold">Edit Exam</h4>
            </div>
            <h1
              className="d-none text text-primary"
              align="center"
              id="h6"
            ></h1>
            <div class="modal-body ">
              <div className="col-10 modal-body">
                <form className="d-flex" id="myform" onClick={handleSubmit}>
                  <div className="col-5">
                    <div className="mb-3">
                      <label
                        htmlFor="exampleInputEmail1"
                        className="form-label float-start"
                        hidden
                      >
                        ExamId*:
                      </label>
                      <input
                        type="text"
                        id="examid"
                        className="form-control "
                        name="examId"
                        placeholder="ExamId eg-1"
                        value={examid}
                        hidden
                        // onChange={(e)=>setFormValues.examId(e.target.value)}
                      />
                      <p id="p1" className="text-danger"></p>
                    </div>
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
                        value={examname}
                        onChange={(e) => setexamname(e.target.value)}
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
                        value={description}
                        onChange={(e)=>setdescription(e.target.value)}
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
                        value={creationdate}
                        onChange={(e)=>setcreationdate(e.target.value)}
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
                        value={expirationdate}
                        onChange={(e)=>setexpirationdate(e.target.value)}
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
                        value={noofquestions}
                        onChange={(e)=>setnoofquestions(e.target.value)}
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
                        readOnly
                        value={durationminutes}
                        onChange={(e)=>setdurationminutes(e.target.value)}
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
                        value={passpercentage}
                        onChange={(e)=>setpasspercentage(e.target.value)}
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
                      <select
                        name="questionsRandomized"
                        id=""
                        className="form-select"
                        value={questionsrandomized}
                        onChange={(e)=>e.target.value}
                      >
                        {" "}
                        QuestionsRandomized:
                        <option value="Y">Yes</option>
                        <option value="N">No</option>
                      </select>
                      <p id="p9" className="text-danger"></p>
                    </div>
                    <div className="mb-3">
                      <label
                        htmlFor="exampleInputEmail1"
                        className="form-label float-start"

                      >
                        Answers Must:
                      </label>
                      <select name="answersMust" id="" className="form-select" value={answersmust} onChange={(e)=>setanswersmust(e.target.value)}>
                   
                        QuestionsRandomized:
                        <option value="Y">Yes</option>
                        <option value="N">No</option>
                      </select>
                      <p id="p10" className="text-danger"></p>
                    </div>
                    <div className="mb-3">
                      <label
                        htmlFor="exampleInputEmail1"
                        className="form-label float-start"
                      >
                        EnableNegativeMark:
                      </label>
                      <select
                        name="enableNegativeMark"
                        id=""
                        className="form-select"
                      >
                       
                        QuestionsRandomized:
                        <option value="Y">Yes</option>
                        <option value="N  ">No</option>
                      </select>
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
                        value={negativemarkvalue}
                        onChange={(e)=>setnegativemarkvalue(e.target.value)}
                      />
                      <p id="p12" className="text-danger"></p>
                    </div>

                    <div>
                      <button className="btn btn-primary offset-9">
                        Submit
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default GetExams;
