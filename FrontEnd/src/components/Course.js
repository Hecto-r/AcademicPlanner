import React, { Component } from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import Draggable from "../Dnd/draggable";
import Modaltest from "./Modaltest";


//MUI stuff
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

const styles = {
  card: {
    marginBottom: 20,
    justifyContent: "center",
    height: 155,
    width: 250
  },
  content: {
    padding: 10,
  },
};

class Course extends Component {
  render() {
    return (
      <div>
        <Draggable id={this.props.course.course.course_id}>
          <Card className={this.props.classes.card}>
            <CardContent className={this.props.classes.content}>
            <Modaltest course_id={this.props.course.course.course_id} units={this.props.course.course.units} description={this.props.course.course.description} pre_req={this.props.course.course.pre_req}/>
              <Typography variant="body1" align="center">
                {this.props.course.course.title}
              </Typography>
              <Typography variant="h5" color="primary" align="center">
                {this.props.course.course.course_id}
              </Typography>

                {this.props.course.status === "Completed" ? (
                  <p style={{ color: 'green' }}>
                  {this.props.course.status}
                  </p>
                  ) : (<p style={{ color: 'red' }}>
                  {this.props.course.status}
                  </p>)
                }

            </CardContent>
          </Card>
        </Draggable>
      </div>
    );
  }
}

export default withStyles(styles)(Course);
