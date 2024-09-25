import React from 'react';

const orderItem = (props) => {
    return(<li>
        {props.item.name}, {props.item.priceWithDiscount.amount} {props.item.priceWithDiscount.currency}
        <button onClick={props.deleteItem(props.item.id.id)} className={"btn btn-danger mx-1"}>Delete</button>

    </li>)

}

export default orderItem;