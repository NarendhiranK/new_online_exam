import React from "react";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../QuestionMaster/QuestionForTopicView.css";
import useStateRef from "react-usestateref";
import { control, pluginName, port, protocol } from "../constants";

const QuestionForTopicView = () => {
  const params = useParams();
  const TopicId = params.topicId;
  const examId = params.examId;
  const [questionData, setQuestionData] = useState([]);
  const [topicName, setTopicName] = useState("");
  const [hasError, setHasError, hasNoError] = useStateRef(false);
  const navigate = useNavigate();


  const questionEdit=(questionId)=>{
    let map={
      questionId:questionId
    }

    
  }

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
        QuestionDetails();
      });
  }

  
  function QuestionDetails() {
    const map = {
      topicId: TopicId,
    };
    fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/viewQuestions", {
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
        console.log("data...>",data);
        const myobj = data.resultMap.listQuestions;
        
        const topicname = data.topicName;
    
        setQuestionData(myobj);
        setTopicName(topicname);
        console.log("topic Name...",topicName);
        console.log("myobj...",myobj);
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
        onClick={()=>navigate(`/admin/updateExam/examdetails/question-topicView/add-questions/${TopicId}/${topicName}`)}
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
                  <button className="btn btn-primary" data-bs-toggle="modal" data-bs-target="" onClick={()=>questionEdit(obj.questionId)}>Edit</button>
                  <button
                    className="btn btn-primary mx-2"
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

          <div>
            {/* modal takes place here */}
          </div>
    </div>
  );
};

export default QuestionForTopicView;
