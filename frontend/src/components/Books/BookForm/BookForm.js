import React from 'react';
import {useNavigate} from 'react-router-dom';

const BookForm = (props) => {

    const navigate = useNavigate();
    const [formData, updateFormData] = React.useState({
        name: "",
        description:"",
        discount:0,
        price:0,
        currency:0,
        bookCount:0,
        category: 0,
        status:0
    })

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const name = formData.name
        const category = formData.category
        const description = formData.description
        const price = formData.price
        const currency = formData.currency
        const discount = formData.discount
        const status = formData.status
        const bookCount = formData.bookCount

        props.onAddBook(name, description,price,currency,discount,status,category,bookCount);
        navigate("/books");
    }

    return (
        <div className="row mt-5">
            <div className="col-md-5">
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Book name</label>
                        <input type="text"
                               className="form-control"
                               id="name"
                               name="name"
                               placeholder="Enter book name"
                               onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="desc">Description</label>
                        <input type="text"
                               className="form-control"
                               id="desc"
                               name="description"
                               placeholder="Enter book description"
                               onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Categories</label>
                        <select name="category" id="category" className="form-control" onChange={handleChange}>
                            {props.categories.map((term, index) => {
                                return <option key={index} value={term}>{term}</option>
                            })}
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="price">Book price</label>
                        <input type="number"
                               className="form-control"
                               id="price"
                               name="price"
                               placeholder="Book price"
                               onChange={handleChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Currency</label>
                        <select name="currency" id="currency" className="form-control" onChange={handleChange}>
                            {props.currency.map((term, index) => {
                                return <option key={index} value={term}>{term}</option>
                            })}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="discount">Price discount</label>
                        <input type="number"
                               className="form-control"
                               id="discount"
                               name="discount"
                               placeholder="Price discount"
                               onChange={handleChange}
                        />
                    </div>


                    <div className="form-group">
                        <label htmlFor="numBooks">Number available books</label>
                        <input type="number"
                               className="form-control"
                               id="numBooks"
                               name="bookCount"
                               placeholder="Number of books"
                               onChange={handleChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Categories</label>
                        <select name="status" id="status" className="form-control" onChange={handleChange}>
                            {props.status.map((term, index) => {
                                return <option key={index} value={term}>{term}</option>
                            })}
                        </select>
                    </div>
                    <button id="submit" type="submit" className="btn btn-primary m-3">Submit</button>
                </form>
            </div>
        </div>
    )
}

export default BookForm;