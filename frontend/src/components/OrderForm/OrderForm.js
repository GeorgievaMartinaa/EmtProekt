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
                    if(item.priceWithDiscount.currency === "MKD")
                        sum+=item.priceWithDiscount.amount
                    else sum+= item.priceWithDiscount.amount*61.5;
                    return (
                        <li>
                            {item.name}, {item.priceWithDiscount.amount} {item.priceWithDiscount.currency}
                            <button onClick={()=>props.deleteItem(item.id.id)}>Delete</button>

                        </li>
                    ); })}
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
