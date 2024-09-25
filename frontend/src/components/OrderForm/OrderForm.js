import React from 'react';

const OrderForm = (props) => {
    console.log(props)
    console.log(props.orderForm)
    let sum =0;
    return (
        <div>
            <h2>My Order</h2>
            <ul>
                {console.log(props.orderForm.items)}
                {props.orderForm.items.map
                ((item) => {
                    {console.log(item)}
                    if(item.book.priceWithDiscount.currency === "MKD")
                        sum+=item.book.priceWithDiscount.amount * item.quantity
                    else sum+= (item.book.priceWithDiscount.amount*61.5) *item.quantity;
                    if(item.book.priceWithDiscount.currency === "MKD"){
                    return (
                        <li>
                            Book: {item.book.name}, {item.book.priceWithDiscount.amount} {item.book.priceWithDiscount.currency}  Quantity: {item.quantity}
                            {item.quantity < item.book.bookCount &&
                            <button
                                onClick={() => props.increaseQuantity(item.book.id.id)}
                                className="btn btn-sm btn-success mx-2"
                            >+</button>}
                            <button
                                onClick={() => props.decreaseQuantity(item.book.id.id)}
                                className="btn btn-sm btn-warning mx-2"
                            >-</button>
                            <button onClick={()=>props.deleteItem(item.book.id.id)} className={"btn mx-1 btn-close "}></button>

                        </li>
                    );}
                else {
                        return (
                            <li>
                               Book: {item.book.name}, {item.book.priceWithDiscount.amount}  {item.book.priceWithDiscount.currency} -> {item.book.priceWithDiscount.amount*61.5} MKD  Quantity: {item.quantity}
                                {item.quantity < item.book.bookCount &&
                                <button
                                    onClick={() => props.increaseQuantity(item.book.id.id)}
                                    className="btn btn-sm btn-success mx-2"
                                >+</button>}

                                <button
                                    onClick={() => props.decreaseQuantity(item.book.id.id)}
                                    className="btn btn-sm btn-warning mx-2"
                                >-</button>

                                <button onClick={()=>props.deleteItem(item.book.id.id)} className={"btn mx-1 btn-close "}></button>

                            </li>
                        );
                    }})}
            </ul>
            <h4> Total price: {sum} MKD</h4>
            {sum > 0 && (
            <button className="btn btn-primary" onClick={() => props.onOrderSubmit(props.orderForm)}>
                Submit Order
            </button>
            )}
        </div>
    );
};

export default OrderForm;
