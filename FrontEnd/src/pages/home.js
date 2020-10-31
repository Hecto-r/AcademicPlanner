import React, { Component } from "react";
import axios from "axios";
import Grid from "@material-ui/core/Grid";
import CourseInfo from "../components/Course";
import Droppable from '../Dnd/Droppable';
import Typography from "@material-ui/core/Typography";
import ProductTable from '../components/ProductTable';

const droppableStyle = {
  backgroundColor: '#555',
  width: '250px',
  height: 'fit-content',
  margin: '32px',
  paddingBottom: '150px',
  paddingTop: '10px',
  borderRadius: '15px',
  boxShadow: '0px 0px 63px 2px rgba(0,0,0,0.75)'
};

let retrieveUser = localStorage.getItem("user")
if(!retrieveUser){
  retrieveUser = 1;
}

class home extends Component {
  state = {
    isLoaded: false,
    courseData: null,
  };

  componentDidMount() {
    axios
      .get("findSHC/"+JSON.parse(retrieveUser).id)
      .then((res) => {
        this.setState({
          isLoaded: true,
          courseData: res.data,
        });
      })
      .catch((err) => console.log(err));
  }



  render() {

    let completedCourses = this.state.courseData ? (
      this.state.courseData.filter((c) => c.status === "Completed").map((c) => <CourseInfo key ={c.course.course_id} course={c}/>).sort((a,b) => 
      (a.key > b.key) ? 1: -1
  )
    ) : (
      <p>Loading...</p>
    );

    let nonPassedCourses = this.state.courseData ? (
      this.state.courseData.filter((c) => c.status === "Failed" || (c.status !== "Completed" && c.status !== "Electives")).map((c) => <CourseInfo key ={c.course.course_id} course={c}/>)
    ) : (
      <p>Loading...</p>
    );

    let electives = this.state.courseData ? (
      this.state.courseData.filter((c) => (c.course.core === false && c.status !== "Completed" && c.status === "Electives") || c.status === "Electives").map((c) => <CourseInfo key ={c.course.course_id} course={c}/>)
    ) : (
      <p>Loading...</p>
    );

    const { isLoaded } = this.state;

    return (
      <Grid container spacing={10} justify='center'>
        <Grid item sm={3} align="center">
          <Typography variant="h4" align="right">
            Electives
          </Typography>
          <Droppable id ="Electives" style={droppableStyle}>
            {electives}
          </Droppable>
        </Grid>
        <Grid item sm={3} align="center">
        <div className="center-col">
          { isLoaded ? (
                <ProductTable products={nonPassedCourses} />    
              ) : <p>t...</p>
          }
        </div>
        </Grid>
        <Grid item sm={3} align="center">
          <Typography variant="h4" align="right">
            Completed
          </Typography>
          <Droppable id ="Completed" style={droppableStyle}>
            {completedCourses}
          </Droppable>
        </Grid>
      </Grid>
    );
  }
}

export default home;
