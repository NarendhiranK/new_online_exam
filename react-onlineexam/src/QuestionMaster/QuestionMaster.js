import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useStateRef from "react-usestateref";
import { control, pluginName, port, protocol } from "../constants";

const QuestionMaster = () => {
  const params = useParams();
  const TopicId = params.topicId;
  console.log("TopicId...>",params.topicId);
  const examId = params.examId;
  const topicName = params.topicName;
  const [questionData, setQuestionData] = useState([]);
  const [enumerationData,setEnumerationData]=useState([]);
  // const [topicName, setTopicName] = useState("");
  const [hasError, setHasError, hasNoError] = useStateRef(false);
  const [hasquestionType, setQuestionType] = useState("01");
  const navigate = useNavigate();
  console.log("State is",hasquestionType);


  function enumerationRecords(){
    fetch("https://localhost:8443/onlineexam/control/retrieveEnumerationListEvent")
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      // setData(data.ExamData);
      // const res = data.ExamData;
      const res=data.resultMap;
      setEnumerationData(res);
      console.log("data...>",enumerationData);
      console.log("res....", res);
    }).catch(err=>console.log(err));
  }

  useEffect(() => {
    enumerationRecords();
  }, []);
  function deleteQuestion(questionId) {
    let map = {
      questionId: questionId,
    };

    fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/deleteQuestionMaster", {
      method: "DELETE",
      credentials: "include",
      body: JSON.stringify(map),
      headers: {
        "Content-type": "application/json",
      },
    })
      .then((reponse) => {
        return reponse.json();
      })
      .then((data) => {
        window.location.reload();
      });
  }

  const submittingForm = (e) => {
    
    e.preventDefault();
    console.log("Submitting form called....>");
    const formElement=document.getElementById('myform');
    const data = new FormData(formElement);
    handleErrors();
    const validateQuestionMaster = (key, value) => {

      switch (key) {
    
        case "questionDetail":
          {
            if (value === null || value === "") {
              document.getElementById("p1").classList.remove("d-none");
              document.getElementById("p1").classList.add("d-block");
              document.getElementById("p1").innerHTML =
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

        

          case "difficultyLevel":
          {
            if (value === null || value === "") {
              document.getElementById("p11").classList.remove("d-none");
              document.getElementById("p11").classList.add("d-block");
              document.getElementById("p11").innerHTML =
                "Please enter a difficulty Level ";
              setHasError(true);
            }
          }
          break;

          case "answerValue":
          {
            if (value === null || value === "") {
              document.getElementById("p12").classList.remove("d-none");
              document.getElementById("p12").classList.add("d-block");
              document.getElementById("p12").innerHTML =
                "Please enter a answerValue";
              setHasError(true);
            }
          }
          break;

        

            case "negativeMarkValue":
              {
                if (value === null || value === "") {
                  document.getElementById("p13").classList.remove("d-none");
                  document.getElementById("p13").classList.add("d-block");
                  document.getElementById("p13").innerHTML =
                    "Please enter a negativeMarkValue";
                  setHasError(true);
                }
                else{
                  let regexx=new RegExp('');
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
   const questionType=data.get("questionType");
    const negativeMarkValue = data.get("negativeMarkValue");

    // const topicId=data.get("topicId");
    // const questionType = document.getElementById("questionType").value;
    // console.log(questionType);
    const map = {
      // questionId: questionId,
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
      topicId: TopicId,
      negativeMarkValue: negativeMarkValue,
    };

    console.log("map",map);

    if (!hasNoError.current) {
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
        .then((response) => {
          console.log("Respponse....>",response);
          return response.json();
          // window.location.reload();
        })
        .then(data=>{
          console.log("Response data....>",data)
          document.getElementById('p1').classList.add('d-none');
          if(data.Error_Msg){
              document.getElementById('p1').classList.remove('d-none');
              document.getElementById('p1').classList.add('d-block');
              document.getElementById('p1').innerHTML="No more questions to be added to this topic..";
          }

          if(data._SUCCESS_MESSAGE){
            document.getElementById('p2').classList.remove('d-none');
            document.getElementById('p2').classList.add('d-block');
            document.getElementById('p2').innerHTML="Question to be added to this topic is done successfully..";
          }
        })

        .catch((err) => console.log("ERROR FROM FETCH", err));
    }

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
      
      document.getElementById("p11").classList.add("d-none");
      document.getElementById("p12").classList.add("d-none");
      document.getElementById("p13").classList.add("d-none");
      

      setHasError(false);
    }
  };

  return (
    <div className="col-10">
      <div className="container my-3">
        <h2 id="p1" className="d-none text-danger" align="center"></h2>
        <h2 id="p2" className="d-none text-primary" align="center"></h2>
        <div className="col-10 mx-auto">
          <form className="d-flex" id="myform" onSubmit={submittingForm}>
            <div className="col-5">
      
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Question Detail:
                </label>
                <textarea
                  className="form-control"
                 
                  name="questionDetail"
                  placeholder="questionDetail"
                  id="questionDetail"
                  rows="3"
                ></textarea>
                <p id="p1" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Answer :
                </label>

                <textarea
                  className="form-control"
                  name="answer"
                  placeholder="answer "
                  id="answer"
                  rows="3"
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
                />
                <p id="p9" className="text-danger"></p>
              </div>

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
                />
                <p id="p12" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                  hidden
                >
                  Topic Id:
                </label>
                <input
                  type="text"
                  placeholder="topic Id"
                  id="topicId"
                  name="topicId"
                  className="form-control"
                  value={TopicId}
                  hidden
                  readOnly
                />

      
              </div>

              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Topic Name:
                </label>
                <input
                  type="text"
                  placeholder="topic Id"
                  id="topicId"
                  name="topicId"
                  className="form-control"
                  value={topicName}
                  readOnly
                />

                             </div>
              <button
                type="submit"
                className="btn btn-primary "
                onClick={()=>navigate(`/admin/updateExam/examdetails/question-topicView/${TopicId}`)}
              >
                Back
              </button>
            </div>

            <div className="col-5 offset-1">
              {/* <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Question Type:
                </label>

                <select
                  className="form-select"
                  id="select"
                  aria-label="Default select example"
                  onChange={(e) => {
                    setQuestionType(e.target.value);
                    console.log(e.target.value);
                  }}>
                  <option  name="singleChoice">
                  singleChoice
                  </option>
                  <option  name="multipleChoice" selected>
                  multipleChoice
                  </option>
                  <option  name="trueorFalse">
                  trueorFalse
                  </option>
                  <option name="fillintheblankhs">
                  fillintheblankhs
                  </option>
                  <option  name="answerinDetail">
                  answerinDetail
                  </option>
                </select>
              </div> */}

              <div className="form-group mt-3">
                <label htmlFor="" className="form-label float-start">Question Type</label>
              <select name="questionType" id="" className="form-select" 
               onChange={(e) => {
                setQuestionType(e.target.value);
                console.log(e.target.value);
              }}>
                Choose Exam
                {enumerationData
                  ? enumerationData.map((obj, value) => {
                      return <option value={obj.enumId} name={obj.description} >{obj.description}</option>;
                    })
                  : ""}
              </select>
            </div>
            
              {
                hasquestionType === "01" || hasquestionType === "02"   ? (
                <div>
                  <div className="mb-3">
                    <label
                      htmlFor="exampleInputEmail1"
                      className="form-label float-start"
                    >
                      Option A:
                    </label>
                    <textarea
                      className="form-control"
                      name="optionA"
                      placeholder="Option A"
                      id="optionA"
                      rows="1"
                    ></textarea>

                    <p id="p3" className="text-danger"></p>
                  </div>
                  <div className="mb-3">
                    <label htmlFor="" className="form-label float-start">
                      Option B:
                    </label>

                    <textarea
                      className="form-control"
                      name="optionB"
                      placeholder="option B"
                      id="optionB"
                      rows="1"
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

                    <textarea
                      className="form-control"
                      name="optionC"
                      placeholder="option C"
                      id="optionC"
                      rows="1"
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

                    <textarea
                      className="form-control"
                      name="optionD"
                      placeholder="option D"
                      id="optionD"
                      rows="1"
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

                    <textarea
                      className="form-control"
                      name="optionE"
                      placeholder="option E"
                      id="optionE"
                      rows="1"
                    ></textarea>

                    <p id="p7" className="text-danger"></p>
                  </div>
                </div>
              ) : (
               <></>
              )}


              {
              hasquestionType === "03" ? (
                <div>
                  <div>
                    <div className="mb-3">
                      <label
                        htmlFor="exampleInputEmail1"
                        className="form-label float-start"
                      >
                        Question Detail:
                      </label>
                      <textarea
                        className="form-control"
                        name="questionDetail"
                        placeholder="questionDetail"
                        id="questionDetail"
                        rows="3"
                      ></textarea>
                      <p id="p2" className="text-danger"></p>
                    </div>
                    <div>
                      <div
                        class="btn-group"
                        role="group"
                        aria-label="Basic checkbox toggle button group"
                      >
                        <input
                          type="radio"
                          class="btn-check"
                          name="btnradio"
                          id="btncheck1"
                          autocomplete="off"
                        />
                        <label class="btn btn-outline-primary" for="btncheck1">
                          True
                        </label>

                        <input
                          type="radio"
                          class="btn-check"
                          name="btnradio"
                          id="btncheck2"
                          autocomplete="off"
                        />
                        <label class="btn btn-outline-primary" for="btncheck2">
                          False
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                <></>
              )}
              
              {/* <p id="p10" className="text-danger"></p> */}

{/*               
                {
                  hasquestionType === "answerinDetail" ? (
                    <div>
                    <div className="mb-3">
                    <label
                      htmlFor="exampleInputEmail1"
                      className="form-label float-start"
                    >
                      Question Detail:
                    </label>
                    <textarea
                      className="form-control"
                      name="questionDetail"
                      placeholder="questionDetail"
                      id="questionDetail"
                      rows="3"
                    ></textarea>
                    <p id="p2" className="text-danger"></p>
                  </div>
                  <div className="mb-3">
                    <label
                      htmlFor="exampleInputEmail1"
                      className="form-label float-start"
                    >
                      Answer :
                    </label>
    
                    <textarea
                      className="form-control"
                      name="answer"
                      placeholder="answer "
                      id="answer"
                      rows="3"
                    ></textarea>
    
                    <p id="p8" className="text-danger"></p>
                  </div>
                </div>
                  ) : (
                    <></>
                  )
                } */}
            
            
              
                {/* {hasquestionType === "fillintheblankhs" ? (
                  <div>
                    <div className="mb-3">
                    <label
                      htmlFor="exampleInputEmail1"
                      className="form-label float-start"
                    >
                      Question Detail:
                    </label>
                    <textarea
                      className="form-control"
                      name="questionDetail"
                      placeholder="questionDetail"
                      id="questionDetail"
                      rows="3"
                    ></textarea>
                    <p id="p2" className="text-danger"></p>
                  </div>
                  <div className="mb-3">
                    <label
                      htmlFor="exampleInputEmail1"
                      className="form-label float-start"
                    >
                      Answer :
                    </label>
    
                    <textarea
                      className="form-control"
                      name="answer"
                      placeholder="answer "
                      id="answer"
                      rows="3"
                    ></textarea>
    
                    <p id="p8" className="text-danger"></p>
                  </div>
                  </div>
                ) : (
                  <></>
                )
                } */}
            

              <div className="mb-3 my-3">
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
                />
                <p id="p13" className="text-danger"></p>
              </div>
              <div>
                <button type="submit" className="btn btn-primary offset-1 my-1">
                  Submit
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default QuestionMaster;
