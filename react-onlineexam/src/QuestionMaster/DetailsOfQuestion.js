import React, { useEffect, useMemo } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";

const DetailsOfQuestion = () => {
  const [data, setData] = useState("");
  const params = useParams();
  const [ansoptionA, setansoptionA] = useState("");
  const [getquestionDetails, setQuestionDetails] = useState([]);
  const questionId = params.questionId;
  const topicName = params.topicName;
  
  const examId = params.examId;
  const navigate = useNavigate();
  console.log("Topic Name....>", topicName);
  console.log("Question Id...>", questionId);
 
  var myobj;

  // var optionA="Hello";

  async function questionDetails() {
    const map = {
      questionId: questionId,
    };
    console.log("map....", map);
    const response = await fetch(
      "https://localhost:8443/onlineexam/control/questionMasterList",
      {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-type": "application/json",
        },
      }
    );

    const result = await response.json();
    const get_result = result.getRecord;
    var topicId=result.getRecord.topicId;
    console.log("Topic Id...>",topicId);
    setQuestionDetails(get_result);
    console.log(get_result);
  }

  useEffect(() => {
    questionDetails();
  }, []);
  console.log(
    "******************************************" + getquestionDetails.questionId
  );

  return (
    <div className="my-5 mx-5">
      <div className="d-flex shadow">
        <p className="text-primary font-weight-bold my-2 mx-2">
          Question Detail -
        </p>
        <div className="my-2 text-uppercase font-weight-bold">
          {getquestionDetails.questionDetail}
        </div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Option A - </p>
        <p className="my-2 ">{getquestionDetails.optionA}</p>
      </div>
      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Option B - </p>
        <div className="my-2">{getquestionDetails.optionB}</div>
      </div>
      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Option C - </p>
        <div className="my-2">{getquestionDetails.optionC}</div>
      </div>
      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Option D - </p>
        <div className="my-2">{getquestionDetails.optionD}</div>
      </div>
      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Option E - </p>
        <div className="my-2">{getquestionDetails.optionE}</div>
      </div>
      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Answer - </p>
        <div className="my-2">{getquestionDetails.answer}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">TopicName - </p>
        <div className="my-2">{topicName}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">QuestionType - </p>
        <div className="my-2">{getquestionDetails.questionType}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">AnswerValue - </p>
        <div className="my-2">{getquestionDetails.answerValue}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">DifficultyLevel - </p>
        <div className="my-2">{getquestionDetails.difficultyLevel}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">Num Answers - </p>
        <div className="my-2">{getquestionDetails.numAnswers}</div>
      </div>

      <div className="d-flex shadow">
        <p className="text-primary my-2 mx-2">NegativeMarkValue - </p>
        <div className="my-2">{getquestionDetails.negativeMarkValue}</div>
      </div>
      <div>
      <button className="btn btn-primary" onClick={()=>navigate(`/admin/updateExam/examdetails/question-topicView/${getquestionDetails.topicId}/${examId}`)}>Back</button>
      </div>

      
    </div>
  );
};

export default DetailsOfQuestion;
