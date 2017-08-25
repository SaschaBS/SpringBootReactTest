import React from 'react';
import Link from 'react-router';

class Message extends React.Component {
  constructor(props) {
    super(props);
  }


  render() {
    // Because `this.handleClick` is bound, we can use it as an event handler.
    return (
      <p>Yeah!</p>
    );
  }
}
export default Message;