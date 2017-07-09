use admin
config = {
    _id: "live_replset",
    members: [
        {_id: 0, host: "db_primary: 27017"},
        {_id: 1, host: "db_third: 27017"},
        {_id: 2, host: "db_second: 27017"}
    ]
};
rs.initiate(config);
rs.status();