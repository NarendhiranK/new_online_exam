import React, { useCallback, useState } from "react";
import "../ExamMaster/Topicpage.css";



const Topicpage = () => {


    const handleSubmit= async(e)=>{
      e.preventDefault();


      const data=new FormData(e.target);

      const topicId=data.get("topicId");
      const topicName=data.get("topicName");

      const map={
        topicId:topicId,
        topicName:topicName
      }

      fetch('https://localhost:8443/onlineexam/control/AddTopicMasterEvent',{
      method: "POST",
      body: JSON.stringify(map),
      headers:{
        'Content-Type':"application/json",
        'Accept': "application/json"
      }
    }).then((response)=>alert("Value added succesfully"))
    .catch(err=>console.log('ERROR FROM FETCH',err))





  }



















  return (
    <div className="row">
      <div className="col-4"></div>
      <div className="col-4 topic ">
        <form onSubmit={handleSubmit}>
          <div className="form-group div2">
            <label for="" class="label1">
              Examid
            </label>
            <p className="lead text-primary">Hello</p>
          </div>
          <div className="form-group div2">
            <label for="" class="label1">
              TopicId
            </label>
            <input
              type="text"
              id=""
              className="form-control finput"
              placeholder="TopicId eg(1)"
              name="topicId"
            />
          </div>
          <div className="form-group div2">
            <label for="" class="label1">
              TopicName
            </label>
            <input
              type="text"
              id=""
              className="form-control finput"
              placeholder="TopicName"
              name="topicName"
            />
          </div>

          <button type="submit" class="btn btn-primary mt-4">
           Back
          </button>

          <button type="submit" class="btn btn-success mt-4 btn2">
            Add Questions
          </button>
        </form>
      </div>
      <div className="col-4"></div>
    </div>
  );
};

export default Topicpage;
