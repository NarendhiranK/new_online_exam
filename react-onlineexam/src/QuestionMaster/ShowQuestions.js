import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import '../ShowQuestions.css'

const ShowQuestions = () => {
  const [data, setData] = useState("");
  const deleteRows = (index) => {
    const updatedRows = [...data];
    updatedRows.splice(index, 1);
    setData(updatedRows);
    // fetch("https://localhost:8443/onlineexam/control/deleteexam", {
    //   method: "DELETE",
    // });
  };

  async function fetchInfo() {
    console.log("fetch obj is called....");

    const response = await fetch(
      "https://localhost:8443/onlineexam/control/questionMasterList"
    );

    const text = await response.json();

    setData(text.listOfQuestions);
    console.log(text.listOfQuestions);
    console.log("listOfQuestions..", text.listOfQuestions);
  }

  useEffect(() => {
    fetchInfo();
  }, []);

  return (
    <div className="container col-10">
      <div class="accordion " id="accordionExample">
        {data
          ? data.map((obj, value) => {
              return (
                <div class="accordion-item border " id="accordionitem">
                 
                  <h6 class="accordion-header " id="headingOne">
                    <button
                      class="accordion-button collapsed text-primary w-100"
                      type="button"
                      data-bs-toggle="collapse"
                      data-bs-target={`#collapseOne${value}`}
                      aria-expanded="true"
                      aria-controls="collapseOne"
                    >
                      <input type="checkbox" name={obj.questionId} id="" /> 
                      <h6 > QuestionNo:{obj.questionId}</h6>
                      
                      <button className="btn btn-primary offset-7">Edit</button>
                      <button className="btn btn-danger">Delete</button>
                    </button>
                  </h6>
                  <div
                    id={`collapseOne${value}`}
                    class="accordion-collapse collapse"
                    aria-labelledby="headingOne"
                    data-bs-parent="#accordionitem"
                  >
                    <div class="accordion-body justify-content-left text-align-left d-grid">
                        <h3 className="text-primary shadow" align="left">
                            Question : {obj.questionDetail}
                        </h3>
                      <h3 className="text-primary shadow" align="left">
                        Answer : {obj.answer}
                      </h3>
                      <p className="shadow" align="left">Option A: {obj.optionA}</p>
                      <p className="p1 border   shadow" align="left">
                        Option B: {obj.optionB}
                      </p>
                      <p className="float-start  shadow" align="left">
                        Option C: {obj.optionC}
                      </p>
                      <p className="float-start  shadow" align="left">
                        Option D : {obj.optionD}
                      </p>
                      <p className="float-start  shadow" align="left">
                        Option E : {obj.optionE}
                      </p>
                      <p className="shadow" align="left">Num Answers : {obj.numAnswers}</p>
                      <p className="shadow" align="left">
                        Question Type: {obj.questionType}
                      </p>
                      <p className="shadow" align="left">
                        Difficulty Level : {obj.difficultyLevel}
                      </p>
                      <p className="shadow" align="left">Answer Value : {obj.answerValue}</p>
                      <p className="shadow" align="left">Topic Id: {obj.topicId}</p>
                      <p className="shadow" align="left">
                        Negative Mark Value : {obj.negativeMarkValue}
                      </p>
                    </div>
                  </div>
                  <div
                    id={`collapseOne${value}`}
                    className="accordion-collapse collapse"
                    aria-labelledby="headingOne"
                    data-bs-parent="accordionExample"
                  >
                    {/* <div className="accordion-body align-items-start border border-primary">
                        <p>Num Answers : {obj.numAnswers}</p>
                        <p>Question Type: {obj.questionType}</p>
                        <p>Difficulty Level : {obj.difficultyLevel}</p>
                        <p>Answer Value : {obj.answerValue}</p>
                        <p>Topic Id: {obj.topicId}</p>
                        <p>Negative Mark Value : {obj.negativeMarkValue}</p>
                     
                    </div> */}
                  </div>
                </div>
              );
            })
          : ""}

        {/* <   div class="accordion-item">
    <h2 class="accordion-header" id="headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
        Accordion Item #2
      </button>
    </h2>
    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>This is the second item's accordion body.</strong> It is hidden by default, until the collapse plugin adds the appropriate classes that we use to style each element. These classes control the overall appearance, as well as the showing and hiding via CSS transitions. You can modify any of this with custom CSS or overriding our default variables. It's also worth noting that just about any HTML can go within the <code>.accordion-body</code>, though the transition does limit overflow.
      </div>
    </div>
  </div> */}
      </div>
      <button className="btn btn-primary my-5 ">Submit</button>
    </div>
  );
};

export default ShowQuestions;
