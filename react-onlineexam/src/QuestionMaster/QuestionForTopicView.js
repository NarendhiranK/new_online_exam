import React from "react";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../QuestionMaster/QuestionForTopicView.css";
import useStateRef from "react-usestateref";

const QuestionForTopicView = () => {
  const params = useParams();
  const TopicId = params.topicId;
  const examId = params.examId;
  const [questionData, setQuestionData] = useState([]);
  const [topicName, setTopicName] = useState("");
  const [hasError, setHasError, hasNoError] = useStateRef(false);
  const navigate = useNavigate();

  function deleteQuestion(questionId) {
    let map = {
      questionId: questionId,
    };

    fetch("https://localhost:8443/onlineexam/control/deleteQuestionMaster", {
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
    const topicId = TopicId;
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
          window.location.reload();
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
      document.getElementById("p10").classList.add("d-none");
      document.getElementById("p11").classList.add("d-none");
      document.getElementById("p12").classList.add("d-none");
      document.getElementById("p13").classList.add("d-none");
      document.getElementById("p14").classList.add("d-none");

      setHasError(false);
    }
  };

  function QuestionDetails() {
    const map = {
      topicId: TopicId,
    };
    fetch("https://localhost:8443/onlineexam/control/viewQuestions", {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(map),
      headers: {
        "Content-type": "application/json",
      },
    })
      .then(response => {
        return response.json();
      })
      .then((data) => {
        const myobj = data.resultMap.listQuestions;
        const topicname = data.resultMap.topicName;
        setQuestionData(myobj);
        setTopicName(topicname);
      });
  }
  useEffect(() => {
    QuestionDetails();
  }, []);
  return (
    <div className="row ">
      <h6 className="text-align-center my-3" align="center">
        TopicName:{topicName}
      </h6>

      <button
        className="btn btn-primary w-25 align-items-right offset-8"
        data-bs-toggle="modal"
        data-bs-target="#div1"
        
      >
        Add Question
      </button>

      <div className="col-12 my-5">
        {questionData ? (
          questionData.map((obj, value) => {
            return (
              <div className="d-flex striped">
                <div className="col-8">
                  <table className="table table-striped">
                    <tr key={obj.questionId}>
                      <td>
                        <div key={value}>Question: {obj.questionDetail}</div>
                      </td>
                    </tr>
                  </table>
                </div>
                <div className="col-4">
                  <button
                    className="btn btn-primary btn1"
                    onClick={() =>
                      navigate(
                        `/admin/updateExam/examdetails/question-topicView/view-questions/${obj.questionId}/${topicName}/${examId}`
                      )
                    }
                  >
                    View Details
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => {
                      deleteQuestion(obj.questionId);
                    }}
                  >
                    Delete
                  </button>
                </div>
              </div>
            );
          })
        ) : (
          <h1 className="" align="center">
            No Questions to show
          </h1>
        )}
      </div>
      <button
        className="btn btn-primary w-25 offset-4 my-3"
        onClick={() => navigate(`/admin/updateExam/examdetails/${examId}`)}
      >
        Back
      </button>

      <div className="col-10">
        <div className="modal fade  bd-example-modal-lg" tabIndex="-1" id="div1">
          <div className="modal-dialog modal-xl">
            <div className="modal-content ">
              <div className="modal-header">
                <h5 className="modal-title offset-5"  >createQuestion</h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body w-100">
                <div className="container my-3">
                  <div className="col-10 mx-auto">
                    <form
                      className="d-flex"
                      id="myform"
                      onSubmit={submittingForm}
                    >
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
                          />

                          <p id="p1" className="text-danger"></p>
                        </div>
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

                      <div className="col-5 offset-1">
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
                            Question Type:
                          </label>

                          <select
                            className="form-select"
                            id="select"
                            aria-label="Default select example"
                           
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
                            readOnly
                          />

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
                          />
                          <p id="p14" className="text-danger"></p>
                        </div>
                        <div>
                          <button
                            type="submit"
                            className="btn btn-primary "
                            data-bs-dismiss="modal"
                          >
                            Close
                          </button>
                          <button
                            type="submit"
                            className="btn btn-primary offset-1"
                          >
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
      </div>
    </div>
  );
};

export default QuestionForTopicView;
