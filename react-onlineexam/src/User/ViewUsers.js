import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
const ViewUsers = () => {
  const navigate = useNavigate()
  const [userList, setUserList] = useState("");
  useEffect(() => {
    fetch("https://localhost:8443/onlineexam/control/viewUsers", {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-type": "application/json",
      },
      //body: JSON.stringify(Object.fromEntries(formdata.entries())),
    })
      .then(response => response.json())
      .then(data => {
        console.log("data", data);
        setUserList(data.userList);
        // console.log(resultData);
        // console.log(resultData.userList[0].firstName)
        //setUserList(resultData.resultList);
      })
      .catch((error) => console.log(error));
  }, []);
  //var resultData;
  return (
    <div className="mx-5">
      <h2>Users :</h2>
      <table className="table-hover  w-75">
        <thead>
          <tr className="bg-primary text-light">
            {/* <th>User ID</th> */}
            <th>First Name</th>
            <th>Last Name</th>
            <th></th>
            <th></th>
            <td></td>
          </tr>
        </thead>
        {userList &&
          userList.map((user) => {
            return (
              <tbody key={user.partyId}>
                <tr className="border border-dark">
                  {/* <td>{user.partyId}</td> */}
                  <td>{user.firstName}</td>
                  <td>{user.lastName}</td>
                  <td>
                    <button className="btn btn-primary" onClick={() => navigate("/admin/assignExam/addExamForUser")}>Add Exam</button>
                    
                  </td>
                  <td>
                    <button className="btn btn-primary">View Exams</button>
                    
                  </td>
                  <td>
                    <button className="btn btn-danger">Delete</button>
                  </td>
                </tr>
              </tbody>
            );
          })}
      </table>
    </div>
  );
};
export default ViewUsers;
