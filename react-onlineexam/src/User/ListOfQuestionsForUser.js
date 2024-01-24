import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { control, pluginName, port, protocol } from '../constants';

const ListOfQuestionsForUser = () => {

  const [questionData, setQuestionData] = useState();
  const params = useParams();
  const examId = params.examId;
  const [counter, setCounter] = useState(10000);


  useEffect(() => {
    counter > 0 && setTimeout(() => setCounter(counter - 1), 1000);
  }, [counter]);

  useEffect(() => {


    let map = {
      examId: examId
    }

    fetch(
      protocol + "://" + window.location.hostname + ":" + port + pluginName + control + "/QuestionsForUserEvent",
      {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-Type": "application/json"
        },
        Accept: "application/json",
      }
    )
      .then(response => {
        return response.json();
      })
      .then(data => {
        console.log("hey data :::::: ", data);



      }).catch(err => console.log(err));

  }, [])


  return (
    <div>
      <div className="col-2">

        <div>Countdown: {counter}</div>



      </div>

      <div className="col-10">


      </div>
    </div>
  )
}

export default ListOfQuestionsForUser
