import { useNavigate, useParams } from "react-router-dom";
import { control, pluginName, port, protocol } from "./constants";
import { useEffect, useState } from "react";

const ViewUserExamDetails = ()=>{
  const [attemptRecords,setAttemptRecords]=useState([]);
  const [noAttemptRecords,setNoAttemptRecords]=useState([]);
  const navigate=useNavigate();
      const {examId} =useParams();
            
              let map={examId:examId};
            
        useEffect(() => {
          fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/checkUserExamAttemptEvent", {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-type": "application/json",
        },
      })

        .then((response) => {
          return response.json();
        })

        .then((data) => {
          
          console.log("response data..!",data);
          
          setAttemptRecords(data.AttemptUserRecords);
          console.log("noAttemptRecord....! " ,data.NoAttemptUserRecords);
          setNoAttemptRecords(data.NoAttemptUserRecords);
          
        }).catch(error => console.log(error));
        },[])  
         ;
    return(
    <div>
     <h1>Users who have attempted :</h1>
     <table className='table table-hover'>
     <thead>
            <tr>
                <th>AttemptUserRecord</th>
                <th>Pass or Fail</th>
                
            </tr>
        </thead>
        <tbody>
             {
                attemptRecords ? (attemptRecords.map((obj,value)=>{

                    return(
                        <tr key={obj.firstName}>
                        <td>{obj.firstName}</td>
                        <td>{obj.userPassed}</td>
                      
                        </tr>
                    );
                })
            ): (
                <p>No exams to show</p>
            )} 
        </tbody>
     </table>
     <h1>Users who have not attempted :</h1>
     <table className='table table-hover'>
     
     <thead>
            <tr>
                <th>NoAttemptUserRecord</th>
                
            </tr>
        </thead>
        <tbody>
        {
                noAttemptRecords ? (noAttemptRecords.map((obj,value)=>{

                    return(
                        <tr key={obj.firstName}>
                        <td>{obj.firstName}</td>
                      
                        </tr>
                    );
                })
            ): (
                <p>No exams to show</p>
            )} 
        </tbody>
     </table>
     <button className="btn btn-primary" onClick={()=>{navigate("/admin/updateExam")}}>Back</button>
     
    </div>
    );
}
export default ViewUserExamDetails;