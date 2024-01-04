import React from "react";
import "bootstrap/dist/css/bootstrap.css";
import useStateRef from "react-usestateref";
import { useState } from "react";
import { useEffect } from "react";

const QuestionMaster = () => {

  const [hasError, setHasError, hasNoError] = useStateRef(false);
  const [data,setData]=useState("");
  const [questionId, setquestionId] = useState("");
  const [questionDetail, setquestionDetail] = useState("");
  const [optionA, setoptionA] = useState("");
  const [optionB, setoptionB] = useState("");
  const [optionC, setoptionC] = useState("");
  const [optionD, setoptionD] = useState("");
  const [optionE, setoptionE] = useState("");
  const [answer, setanswer] = useState("");
  const [numAnswers, setnumAnswers] = useState("");
  const [questionType, setquestionType] = useState("");
  const [difficultyLevel, setdifficultyLevel] = useState("");
  const [answerValue, setanswerValue] = useState("");
  const [topicId, settopicId] = useState("");
  const [negativeMarkValue, setnegativeMarkValue] = useState("");


  
  async function fetchInfo() {
    console.log("fetch obj is called....");

    const response = await fetch(
      "https://localhost:8443/onlineexam/control/topicMasterList"
    );

    const text = await response.json();
    
    setData(text.ListOfTopic);
    console.log("myexamdata..", text.ListOfTopic);
   
  }

  useEffect(() => {
    fetchInfo();
    
  }, []);


 
  const submittingForm = (e) => {
    e.preventDefault();
    alert("form submitted successfully....")
    const data = new FormData(e.target);
    handleErrors();
    const validateQuestionMaster = (key, value) => {
      switch (key) {
        case "questionId":
          if (value === null || value === "") {
            document.getElementById("p1").classList.remove("d-none");
            document.getElementById("p1").classList.add("d-block");
            document.getElementById("p1").innerHTML =
              "Please enter a QuestionId";
            setHasError(true);
          } else {
            let questionId = new RegExp("^[0-9]");
            if (!questionId.test(value)) {
              document.getElementById("p1").classList.remove("d-none");
              document.getElementById("p1").classList.add("d-block");
              document.getElementById("p1").innerHTML =
                "Please enter a only numbers";
              setHasError(true);
            }
          }
          break;

        case "questionDetail":
          {
            if (value === null || value === "") {
              document.getElementById("p2").classList.remove("d-none");
              document.getElementById("p2").classList.add("d-block");
              document.getElementById("p2").innerHTML =
                "Please enter a Question Name?";
              setHasError(true);
            }
          }
          break;

        case "optionA":
          {
            if (value === null || value === "") {
              document.getElementById("p3").classList.remove("d-none");
              document.getElementById("p3").classList.add("d-block");
              document.getElementById("p3").innerHTML =
                "Please enter a Option A";
              setHasError(true);
            }
          }
          break;

        case "optionB":
          {
            if (value === null || value === "") {
              document.getElementById("p4").classList.remove("d-none");
              document.getElementById("p4").classList.add("d-block");
              document.getElementById("p4").innerHTML =
                "Please enter a Option B";
              setHasError(true);
            }
          }
          break;

        case "optionC":
          {
            if (value === null || value === "") {
              document.getElementById("p5").classList.remove("d-none");
              document.getElementById("p5").classList.add("d-block");
              document.getElementById("p5").innerHTML =
                "Please enter a Option C";
              setHasError(true);
            }
          }
          break;

        case "optionD":
          {
            if (value === null || value === "") {
              document.getElementById("p6").classList.remove("d-none");
              document.getElementById("p6").classList.add("d-block");
              document.getElementById("p6").innerHTML =
                "Please enter a Option D";
              setHasError(true);
            }
          }
          break;

        case "optionE":
          {
            if (value === null || value === "") {
              document.getElementById("p7").classList.remove("d-none");
              document.getElementById("p7").classList.add("d-block");
              document.getElementById("p7").innerHTML =
                "Please enter a Option E";
              setHasError(true);
            }
          }
          break;

        case "answer":
          {
            if (value === null || value === "") {
              document.getElementById("p8").classList.remove("d-none");
              document.getElementById("p8").classList.add("d-block");
              document.getElementById("p8").innerHTML = "Please enter a Answer";
              setHasError(true);
            }
          }
          break;

        case "numAnswers":
          {
            if (value === null || value === "") {
              document.getElementById("p9").classList.remove("d-none");
              document.getElementById("p9").classList.add("d-block");
              document.getElementById("p9").innerHTML =
                "Please enter a Num of Answers";
              setHasError(true);
            }
          }
          break;

        case "topicId":
          {
            if (value === null || value === "") {
              document.getElementById("p13").classList.remove("d-none");
              document.getElementById("p13").classList.add("d-block");
              document.getElementById("p13").innerHTML =
                "Please enter a topic Id";
              setHasError(true);
            }
          }
          break;

        default: {
          console.log("form submiited");
        }
      }
    };
    const myobject = Object.fromEntries(data.entries());
    Object.entries(myobject).map(([key, value], keyIndex) => {
      validateQuestionMaster(key, value);
    });
    const questionId = data.get("questionId");
    console.log("questionId-----", questionId);
    const questionDetail = data.get("questionDetail");
    const optionA = data.get("optionA");
    const optionB = data.get("optionB");
    const optionC = data.get("optionC");
    const optionD = data.get("optionD");
    const optionE = data.get("optionE");
    const answer = data.get("answer");
    const numAnswers = data.get("numAnswers");
    const difficultyLevel = data.get("difficultyLevel");
    const answerValue = data.get("answerValue");
    const topicId = data.get("topicId");
    const negativeMarkValue = data.get("negativeMarkValue");
    const questionType = document.getElementById("questionType").value;
    console.log(questionType);
    const map = {
      questionId: questionId,
      questionDetail: questionDetail,
      optionA: optionA,
      optionB: optionB,
      optionC: optionC,
      optionD: optionD,
      optionE: optionE,
      answer: answer,
      numAnswers: numAnswers,
      questionType: questionType,
      difficultyLevel: difficultyLevel,
      answerValue: answerValue,
      topicId: topicId,
      negativeMarkValue: negativeMarkValue,
    };

    console.log(map);
    if(!hasNoError.current){
      fetch(
        "https://localhost:8443/onlineexam/control/CreateorUpdateQuestionMasterEvent",
        {
          method: "POST",
          body: JSON.stringify(map),
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }
      )
        .then((response) =>{
        
          alert("Value added succesfully");
          Window.reload();
      } )
        .catch((err) => console.log("ERROR FROM FETCH", err));
    }
  

    function handleErrors() {
      document.getElementById("p1").classList.add("d-none");
      document.getElementById("p2").classList.add("d-none");
      document.getElementById("p2").classList.innerHTML = "";
      document.getElementById("p3").classList.add("d-none");
      document.getElementById("p3").classList.innerHTML = "";
      document.getElementById("p4").classList.add("d-none");
      document.getElementById("p4").classList.innerHTML = "";
      document.getElementById("p5").classList.add("d-none");
      document.getElementById("p5").classList.innerHTML = "";
      document.getElementById("p6").classList.add("d-none");
      document.getElementById("p6").classList.innerHTML = "";
      document.getElementById("p7").classList.add("d-none");
      document.getElementById("p7").classList.innerHTML = "";
      document.getElementById("p8").classList.add("d-none");
      document.getElementById("p8").classList.innerHTML = "";
      document.getElementById("p9").classList.add("d-none");
      document.getElementById("p9").classList.innerHTML = "";
      document.getElementById("p10").classList.add("d-none");
      document.getElementById("p10").classList.innerHTML = "";
      document.getElementById("p11").classList.add("d-none");
      document.getElementById("p11").classList.innerHTML = "";
      document.getElementById("p12").classList.add("d-none");
      document.getElementById("p12").classList.innerHTML = "";
      document.getElementById("p13").classList.add("d-none");
      document.getElementById("p13").classList.innerHTML = "";
      document.getElementById("p14").classList.add("d-none");
      document.getElementById("p14").classList.innerHTML = "";
      setHasError(false);
    }
  };

  return (
    <div className="container my-3">
      <div className="col-10 mx-auto">
      <legend className="bg-primary text-light border rounded-pill">Create Question </legend>
        <form className="d-flex" id="myform" onSubmit={submittingForm}>
          <div className="col-5">
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                QuestionId*:
              </label>
              <input
                type="text"
                id="questionId"
                className="form-control text-decoration-none"
                placeholder="questionId * eg(1)"
                min={0}
                name="questionId"
                // value={examid}
                 onChange={(e) => setquestionId(e.target.value)}
              />
              {/* <div class="mb-3">
                <label for="" class="form-label"></label>
                
              </div> */}

              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Question Detail:
              </label>
              {/* <input
                type="text"
                id="questionDetail"
                className="form-control"
                placeholder="Enter the Detail of the Question"
                name="questionDetail"
                // value={examname}
                // onChange={(e) => setexamname(e.target.value)}
              /> */}

              <textarea
                class="form-control"
                name="questionDetail"
                placeholder="questionDetail"
                id="questionDetail"
                rows="3"
                onChange={(e) => setquestionDetail(e.target.value)}
              ></textarea>
              <p id="p2" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Option A:
              </label>
              <textarea
                class="form-control"
                name="optionA"
                placeholder="Option A"
                id="optionA"
                rows="1"
                onChange={(e) => setoptionA(e.target.value)}
              ></textarea>
              {/* <input
                type="text"
                id="optionA"
                className="form-control"
                placeholder="Option A"
                name="optionA"
                // value={description}
                // onChange={(e) => setdescription(e.target.value)}
              /> */}
              <p id="p3" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label htmlFor="" className="form-label float-start">
                Option B:
              </label>
              {/* <input
                type="text"
                id="optionB"
                className="form-control"
                name="optionB"
                placeholder="option B"
                // value={creationdate}
                // onChange={(e) => setcreationdate(e.target.value)}
              /> */}
              <textarea
                class="form-control"
                name="optionB"
                placeholder="option B"
                id="optionB"
                rows="1"
                onChange={(e) => setoptionB(e.target.value)}
              ></textarea>

              <p id="p4" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                optionC:
              </label>
              {/* <input
                type="text"
                id="optionC"
                className="form-control"
                name="optionC"
                placeholder="option C"
                // value={expirationdate}
                // onChange={(e) => setexpirationdate(e.target.value)}
              /> */}
              <textarea
                class="form-control"
                name="optionC"
                placeholder="option C"
                id="optionC"
                rows="1"
                onChange={(e) => setoptionC(e.target.value)}
              ></textarea>

              <p id="p5" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputPassword1"
                className="form-label float-start"
              >
                Option D:
              </label>
              {/* <input
                type="text"
                id="optionD"
                className="form-control"
                placeholder="Option D"
                name="optionD"
                // value={noofquestions}
                // onChange={(e) => setnoofquestions(e.target.value)}
              /> */}
              <textarea
                class="form-control"
                name="optionD"
                placeholder="option D"
                id="optionD"
                rows="1"
                onChange={(e) => setoptionD(e.target.value)}
              ></textarea>

              <p id="p6" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputPassword1"
                className="form-label float-start"
              >
                Option E:
              </label>
              {/* <input
                type="text"
                id="optionE"
                className="form-control"
                placeholder="Option E"
                name="optionE"
                // value={noofquestions}
                // onChange={(e) => setnoofquestions(e.target.value)}
              /> */}
              <textarea
                class="form-control"
                name="optionE"
                placeholder="option E"
                id="optionE"
                rows="1"
                onChange={(e) => setoptionE(e.target.value)}
              ></textarea>

              <p id="p7" className="text-danger"></p>
            </div>
          </div>

          <div className="col-5 offset-1">
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Answer :
              </label>
              {/* <input
                type="text"
                id="answer"
                placeholder="answer"
                name="answer"
                className="form-control"
                // value={durationminutes}
               onChange={(e) => setdurationminutes(e.target.value)}
              /> */}
              <textarea
                class="form-control"
                name="answer"
                placeholder="answer "
                id="answer"
                rows="3"
                onChange={(e) => setanswer(e.target.value)}
              ></textarea>

              <p id="p8" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                NumAnswers:
              </label>
              <input
                type="text"
                id="numAnswers"
                placeholder="num of answers"
                name="numAnswers"
                className="form-control"
                // value={passpercentage}
                onChange={(e) => setnumAnswers(e.target.value)}
              />
              <p id="p9" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Question Type:
              </label>

              <select
                class="form-select"
                id="select"
                aria-label="Default select example"
                onSelect={(questionType)=>setquestionType(questionType.value)}
              >
                <option id="questionType" value="singleChoice">
                  Single Choice
                </option>
                <option id="questionType" value="multipleChoice">
                  Multiple Choice
                </option>
                <option id="questionType" value="trueorFalse">
                  True or False
                </option>
                <option id="questionType" value="fillintheblankhs">
                  Fill in the Blankhs
                </option>
                <option id="questionType" value="answerindetail">
                  Answer in Detail
                </option>
                {/* <option value="3">Three</option> */}
              </select>
            </div>
            <p id="p10" className="text-danger"></p>

            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Difficulty Level:
              </label>
              <input
                type="text"
                name="difficultyLevel"
                placeholder="difficulty level"
                id="difficultyLevel"
                className="form-control"
                // value={answersmust}
                onChange={(e) => setdifficultyLevel(e.target.value)}
              />
              <p id="p11" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Answer Value:
              </label>
              <input
                type="text"
                name="answerValue"
                placeholder="answer value"
                id="answerValue"
                className="form-control"
                // value={enablenegativemark}
                onChange={(e) => setanswerValue(e.target.value)}
              />
              <p id="p12" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Topic:
              </label>
              {/* <input
                type="text"
                placeholder="topic Id"
                id="topicId"
                name="topicId"
                className="form-control"
                // value={negativemarkvalue}
                onChange={(e) => settopicId(e.target.value)}
              /> */}
              
              <select name=""  id="">Topic 
               
                { data ? data.map((obj,value)=>{
                           
                    return(
                              <option value={obj.topicId}>Topic Id-{obj.topicId} Topic Name- {obj.topicName}</option>
                      )
                }):""}
                </select>
          
              <p id="p13" className="text-danger"></p>
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
                id="negativeMarkValue"
                name="negativeMarkValue"
                className="form-control"
                // value={negativemarkvalue}
               onChange={(e) => setnegativeMarkValue(e.target.value)}
              />
              <p id="p14" className="text-danger"></p>
              
            </div>
          <div>
            
              <button type="submit" className="btn btn-primary ">
                Finish
              </button>
            <button type="submit" className="btn btn-primary offset-1">
                Add Question
              </button>
            
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default QuestionMaster;
