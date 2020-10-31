import React from 'react';
import PropTypes from 'prop-types';
import axios from "axios";

const currentUser = JSON.parse(localStorage.getItem('user'));
let courseId;

export default class Droppable extends React.Component{

    drop = (e) => {
        e.preventDefault();
        const data = e.dataTransfer.getData('transfer');
        e.target.appendChild(document.getElementById(data));
        this.update(document.getElementById(data))
    }

    allowDrop = (e) => {
        e.preventDefault(e);
    }

    update = (e) => {
        let result = courseId.filter(obj => (obj.key === e.id))
        courseId = result[0].props.course.id
        console.log(this.props.id)
        console.log(e.id)
        console.log(currentUser.id)
        console.log(courseId)

        if((this.props.id === "Electives" && result[0].props.course.course.core === true) || (this.props.id === "Electives" && result[0].props.course.status === "Completed")){
            return;
        }

        axios({
            method: 'put',
            url: '/updateSHC/' + courseId,
            data: {
                student: {
                    student_id: currentUser.id
                },
                course: {
                    course_id: e.id
                },
                status: this.props.id,
                semester: this.props.id
            }
          });
          
    }

    getcourseId = (e) =>{
        courseId = this.props.children
    }

    render(){
        return(
            <div id={this.props.id} onDrop={this.drop} onDragOver={this.allowDrop} style={this.props.style} onMouseDown={this.getcourseId}>
                {this.props.children}
            </div>
        )
    }
}

Droppable.propTypes = {
    id: PropTypes.string,
    style: PropTypes.object,
    children: PropTypes.node,
}