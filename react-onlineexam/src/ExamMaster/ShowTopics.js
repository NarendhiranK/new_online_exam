import React from "react";
import { useState, useEffect } from "react";
import { control, pluginName, port, protocol } from "../constants";

const ShowTopics = () => {
  const [data, setData] = useState("");
  const deleteRows = (index) => {
    const updatedRows = [...data];
    updatedRows.splice(index, 1);
    setData(updatedRows);
    fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/deleteexam", {
      method: "DELETE",
    });
  };

  async function fetchInfo() {
    console.log("fetch obj is called....");

    const response = await fetch(
      protocol+"://"+localhost+":"+port+pluginName+control+"/topicMasterList"
    );

    const text = await response.json();

    setData(text.ListOfTopic);
    console.log("myexamdata..", text.ListOfTopic);
  }

  useEffect(() => {
    fetchInfo();
  }, []);

  return (
    <div className="container mx-5 my-5 px-5">
      <div className="col-10">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>TopicId</th>
              <th>TopicName</th>
              <th></th>
              <th></th>
              
            </tr>
          </thead>

          <tbody>
            {data
              ? data.map((obj, value) => (
                  <tr>
                    <td>{obj.topicId}</td>
                    <td>{obj.topicName}</td>
                    <td>
                      <button className="btn btn-primary">Edit</button>
                    </td>
                    <td>
                      <button
                        className="btn btn-danger"
                        onClick={() => deleteRows(value)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              : ""}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ShowTopics;
