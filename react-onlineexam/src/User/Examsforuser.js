import React, { useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

const Examsforuser = () => {


    const params = useParams();
    const partyId=params.partyId;
    const firstName=params.firstName;
    console.log("The party first name is...",firstName);
    console.log("Hello party Id",partyId);
    const map={
        partyId : partyId,
    }

    useEffect((map)=>{

        fetch("").then(response=>response.json()).then(data=>console.log(data)).catch(err=>console.log(err));
    })
  return (
 
    <div>
       <h1>exams for user</h1>
    </div>

  )
}

export default Examsforuser
