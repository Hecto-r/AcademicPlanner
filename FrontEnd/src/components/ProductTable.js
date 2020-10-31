import React, { Component } from 'react';
import CatSection from './CatSection';

export default class ProductTable extends Component {
    constructor( props ) {
        super( props );
    
        this.keyCount = 0;
        this.getKey = this.getKey.bind(this);
    }

    getKey(){
        return this.keyCount++;
    }
    render() {
        const categories = this.props.products.reduce((allProducts, current) => {
            return allProducts.includes(current.props.course.semester) ? allProducts : allProducts.concat([current.props.course.semester]);
        }, []);
        return (
            <div>
                
                {
                    categories.map((category) => {
                        // filter out the products of the current category
                        const products = this.props.products.filter(prod => prod.props.course.semester === category);
                        // now render only those filtered out products
                        // and display them in the CatSection component
                        return (
                            
                            <CatSection key={this.getKey()}
                                products={products}
                            />
                            
                        )
                    })
                }
            </div>
        )
    }
}