import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Tabs, Tab } from 'react-bootstrap';

import { userActions, mbrActions } from '../_actions';

class MBRHomePage extends React.Component {
    
    constructor(props) {
        super(props);

        this.handleChange = this.handleChange.bind(this);

        this.state = {
            name: '',
            mortgageValue: '',
            houseId: '',
            submittedForm: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitForm = this.handleSubmitForm.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmitForm(e) {
        e.preventDefault();

        this.setState({ submittedForm: true });
        const { name, mortgageValue, houseId } = this.state;
        const { dispatch } = this.props;
        if (name && mortgageValue && houseId) {
            dispatch(mbrActions.submit(name, mortgageValue, houseId));
        }
    }

    render() {
        const { user } = this.props;
        const { name, mortgageValue, houseId, submittedForm } = this.state;
        return (
            <div>
                <h1>MBR</h1>
                <p>
                    <Link to="/mbr/login">Logout</Link>
                </p>
                <Tabs defaultActiveKey={1} id="mbr-tabs">
                    <Tab eventKey={1} title="New Request">
                        <form name="form" onSubmit={this.handleSubmitForm}>
                            <div className={'form-group' + (submittedForm && !name ? ' has-error' : '')}>
                                <label htmlFor="name">Name</label>
                                <input type="text" className="form-control" name="name" value={name} onChange={this.handleChange} />
                                {submittedForm && !name &&
                                    <div className="help-block">Name is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mortgageValue ? ' has-error' : '')}>
                                <label htmlFor="mortgageValue">Mortgage Value ($)</label>
                                <input type="mortgageValue" className="form-control" name="mortgageValue" value={mortgageValue} onChange={this.handleChange} />
                                {submittedForm && !mortgageValue &&
                                    <div className="help-block">Mortgage value is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !houseId ? ' has-error' : '')}>
                                <label htmlFor="houseId">House ID</label>
                                <input type="text" className="form-control" name="houseId" value={houseId} onChange={this.handleChange} />
                                {submittedForm && !houseId &&
                                    <div className="help-block">House ID is required</div>
                                }
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </form>
                    </Tab>
                    <Tab eventKey={2} title="Request Status">
                        TODO: Request Status
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { authentication } = state;
    const { user } = authentication;
    return {
        user
    };
}

const connectedMBRHomePage = connect(mapStateToProps)(MBRHomePage);
export { connectedMBRHomePage as MBRHomePage };