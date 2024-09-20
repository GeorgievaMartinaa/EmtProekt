import React from 'react';

const bookTerm = (props) => {
    console.log(props)
    return (
            <tr>
                <td className={"w-auto"}>{props.term.name}</td>
                <td className={"w-25"}>{props.term.description}</td>
                <td>{props.term.category}</td>
                <td>{props.term.price.amount} {props.term.price.currency}</td>
                <td>{props.term.discount}%</td>
                <td>{props.term.priceWithDiscount.amount} {props.term.priceWithDiscount.currency}</td>
                <td>{props.term.status}</td>
                <td>
                {props.term.bookCount > 0 && (
                    <a title={"Buy"} className={"btn btn-success"}
                       onClick={() => props.onBuy(props.term)}>
                    Buy
                </a>)}
                <a title={"Delete"} className={"btn btn-danger mx-1"}
                   onClick={() => props.onDelete(props.term.id.id)}>
                    Delete
                </a>
                </td>

            </tr>
    )
}

export default bookTerm;