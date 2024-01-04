import React from 'react'

const TopicPage = () => {
  return (
    <div>
        <div className="col-4">
        <div className="mb-3">
              <label htmlFor="exampleInputEmail1" className="form-label float-start">
                Exam Name:
              </label>
              <p>Tamil Exam</p>
            </div>
            <div className="mb-3">
              <label htmlFor="exampleInputEmail1" className="form-label float-start">
               Topic Name:
              </label>
              <input
                type="text"
                name="Topic Name"
                placeholder="Topic Name"
                id="topicName"
                className="form-control"
                // value={answersmust}
                // onChange={(e) => setanswersmust(e.target.value)}
              />
              <p id="p10" className="text-danger"></p>
            </div>
          
            <div className="mb-3">
                <button className="btn btn-primary">Add Question</button>
                <button className="btn btn-primary">Add Another topic</button>
                <button className="btn btn-danger">Delete</button>


            </div>
        </div>
    </div>
  )
}

export default TopicPage
