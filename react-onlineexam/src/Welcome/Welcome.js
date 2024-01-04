import React from 'react'
import { Link } from 'react-router-dom'

const Welcome = () => {
  return (
    <div>
    <h1 className=' my-5' align="center">Welcome Admin ..!!</h1>
    <h6 className='my-5' align="center">Click to view exams<Link to="/admin/updateExam"  align="center"><button className='btn-primary'>View exams</button></Link></h6>
    </div>
  )
}

export default Welcome
 