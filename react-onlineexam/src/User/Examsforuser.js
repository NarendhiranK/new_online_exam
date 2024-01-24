import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

const Examsforuser = () => {


  const navigate = useNavigate();
  const params = useParams();
  const [data, setData] = useState();
  const partyId = params.partyId;
  const firstName = params.firstName;
  console.log("The party first name is...", firstName);
  console.log("Hello party Id", partyId);


  useEffect(() => {
    let map = {
      partyId: partyId
    }

    fetch(
      "https://localhost:8443/onlineexam/control/RetrieveExamsForUserEvent",
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
        console.log("data", data);
        console.log(data.resultMap.examDetailsMap);
        setData([...data.resultMap.examDetailsMap]);
      }).catch(err => console.log(err));

  }, [])
  return (

    <div className='mx-5 my-5'>
      <h4>UserName : {firstName} </h4>
      <table className='table table-hover'>
        <thead>
          <tr>
            <th>Exam Name</th>
            <th>Edit exam details for the user</th>
            <th>Remove user</th>
          </tr>
        </thead>
        <tbody>
          {
            data ? (data.map((obj, value) => {

              return (
                <tr>
                  {/* <td>{obj.examId}</td> */}
                  <td>{obj.examName}</td>
                  <td><button className="btn btn-primary" onClick={() => navigate(`/admin/assignExam/addExamForUser/${obj.partyId}/${obj.examId}`)}>Edit</button></td>
                  <td><button className="btn btn-danger">Remove user</button></td>
                </tr>
              );
            })
            ) : (
              <p>No exams to show</p>
            )}
        </tbody>
      </table>
      <button className="btn btn-primary offset-4" onClick={() => {
        navigate("/admin/assignExam");
      }} >Back</button>
    </div>

  )
}

export default Examsforuser
