import React from "react";
import Book from "../BookTerm/book"
import BookForm from "../BookForm/BookForm";
import {Link} from "react-router-dom";

const Books = (props) => {
    console.log(props)


    return (
        <div className={"container mm-4 mt-5"}>
            <div className={"row"}>
                <h3>Category filter</h3>
                <div className="form-group">
                    <select name="category" id="category" className="form-control">
                        {props.categories.map((term, index) => {
                            return <option key={index} value={term}>{term}</option>
                        })}
                    </select>
                    <button onClick={props.submitFilter} className={"btn btn-success"}>Submit</button>
                    <button onClick={props.loadBooks} className={"mx-2 btn btn-light "}>Clear Filter</button>
                </div>

            </div>
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Description</th>
                            <th scope={"col"}>Category</th>
                            <th scope={"col"}>Price</th>
                            <th scope={"col"}>Discount</th>
                            <th scope={"col"}>Discounted Price</th>
                            <th scope={"col"}>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.books.map((term) => {
                            {
                                console.log(term)
                            }
                            return (
                                <Book key={term.id} term={term} onBuy={props.onBuy} onDelete={props.onDelete}/>
                            );
                        })}
                        <div className="col mb-3">
                            <div className="row">
                                <div className="col-sm-12 col-md-12">
                                    <Link className={"btn btn-block btn-dark"} to={"/books/add"}>Add new
                                        book</Link>
                                </div>
                            </div>
                        </div>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    )

}

export default Books;