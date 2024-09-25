import axios from '../custom-axios/axios';
import axios2 from '../custom-axios/axios2';


const BookOrderService = {
    fetchBooks:() => {
        return axios.get(`/book`);
    },
    fetchBooksByCategory:(category) => {
        return axios.get(`/book`, {
            params: {
                category: category
            }
        });
    },

    fetchCategories:() => {
        return axios.get(`/book/categories`)
    },

    fetchCurrencies:() => {
        return axios.get(`/book/currency`)
    },

    fetchStatus:() => {
        return axios.get(`/book/status`)
    },


    deleteBook: (id) => {
        return axios.post(`/book/delete/${id}`)
    },

    placeOrder: (orderData) => {
        console.log(orderData.currency)
        console.log(orderData.items)
        return axios2.post(`/order/place`,orderData);
    },

    addBook:(name, description,price,currency,discount,status,category,bookCount) =>{
        return axios.post(`/book/create`,
            {
                "name": name,
                "description": description,
                "price": price,
                "currency": currency,
                "discount": discount,
                "status": status,
                "category": category,
                "bookCount": bookCount
            });
    }


}

export default BookOrderService;