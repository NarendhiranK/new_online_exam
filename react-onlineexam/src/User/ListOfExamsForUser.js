import React, { useEffect, useState } from 'react'

const ListOfExamsForUser = () => {
    const [examList, setExamList] = useState("");
    function examsList() {
        fetch("https://localhost:8443/onlineexam/control/examsForUserEvent", {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                console.log("data", data);
                console.log("output",data.examDetailsResultMap.perExamDetails);
                const output=data.examDetailsResultMap.perExamDetails;
                console.log("examname...>",output.examName);
                setExamList(output);
                console.log("examlist......>",examList);
                console.log("State",examList);
                console.log("state description...>",examList.description);
            })
            .catch((error) => console.log(error));
    }
    useEffect(() => {
        examsList()
    }, [])

    console.log("EXAMNAME::::::::::::::::::::::::::::::",examList.examName);

    return (
        <div>
            <h2>List of Exams alloted for you : </h2>
            <table className="table-hover  w-75">
                <thead>
                    <tr className="bg-primary text-light">
                        <th>Exam Name</th>
                        <th>Duration</th>
                        <th>Expiration Date</th>
                        <th></th>
                        <th></th>
                        <td></td>
                    </tr>
                </thead>
                {/* {examList ? (examList.map((exam,value) => {
                    return (
                        <tbody key={exam.examId}>
                            <tr className="border border-dark">
                                <td>{exam.examName}</td>
                                <td>{exam.description}</td>
                                <td>{exam.expirationDate}</td>

                            </tr>
                        </tbody>
                    );
                })) : ("No Exams  has been assigned")} */}
            </table>
        </div>
    )
}

export default ListOfExamsForUser
