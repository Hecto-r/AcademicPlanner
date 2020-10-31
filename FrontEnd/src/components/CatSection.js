import React from 'react';
import CourseInfo from './Course';
import Droppable from '../Dnd/Droppable';
import Typography from "@material-ui/core/Typography";

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

export default function CatSection(props) {

    // destructure the products
    const { products } = props;
    products.sort((a,b) => 
        (a.key > b.key) ? 1: -1
    );
    const firstProduct = products[0];
    return (
        <div>
            <Typography variant="h4" align="center">
                {firstProduct.props.course.semester}
            </Typography>
            <Droppable id = {firstProduct.props.course.semester} style={droppableStyle}>
                {products.map(product => <CourseInfo key ={product.props.course.course.course_id} course={product.props.course}/>)}
            </Droppable>

        </div>

    )
}