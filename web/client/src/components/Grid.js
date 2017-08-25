import React from 'react';
import ReactDataGrid from 'react-data-grid';

class Grid extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            persons: [],
            size: 25,
            page:1,
            columns: [
                {key: 'first_name', name: 'First Name'},
                {key: 'last_name', name: 'Last Name'},
                {key: 'company_name', name: 'company_name'},
                {key: 'address', name: 'address'},
                {key: 'city', name: 'city'},
                {key: 'county', name: 'county'},
                {key: 'state', name: 'state'},
                {key: 'zip', name: 'zip'},
                {key: 'phone1', name: 'phone1'},
                {key: 'phone2', name: 'phone2'},
                {key: 'email', name: 'email'},
                {key: 'web', name: 'web'}
            ]
        };
        this.componentDidMount.bind(this);
    }


    componentDidMount() {

        var request = new Request('http://localhost:8080/person/?size='+this.state.size + '&page=' + this.state.page, {
            method: 'GET'
        })


        fetch(request).then((resp) => resp.json())
        .then((data) => {
                this.setState({persons: data._embedded.person})
            }).catch(function(error) {
                console.log(error);
            });
    }

    rowGetter(i) {
        return this.state.persons[i];
    }


    render() {
        return (
            <ReactDataGrid enableCellSelect={true} enableRowSelect={true} columns={this.state.columns} rowGetter={this.rowGetter.bind(this)} minHeight={500} rowsCount={this.state.persons.length}/>
        );

    }

}

export default Grid;