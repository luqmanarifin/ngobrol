# ngobrol
A repository of cool messaging app, including server and client source code. Using RabbitMQ technology at server-side. 

# message structure

## Register request
```
{
    method: "register",
    username:
    password:
}
```

## Register reply
```
{
    method: register_reply
    status: "ok/error"
    description: "pendaftaran berhasil/pendaftaran gagal"
}
```


## login
```
{
    method: "login"
    username:
    password:
}
```

## login reply
```
{
    method: login_reply
    status: "ok/error"
    description "login berhasil/login gagal"
    token: token
}
```

## add friend
```
{
    method: "add_friend"
    username_from:
    username_to 
}
```

## add friend reply
```
{
    method: add_friend_reply
    status: "ok/error"
    description: "teman berhasil ditambahkan/username tidak ada/gagal"
}
```

## get friend
```
{
    method: "get_friend"
    username:
}
```

## get friend reply
```
{
    method: get_friend_reply
    status: 
    usernames: list username
}
```

## create group
```
{
    method: "create_group"
    username:
    group_name:
}
```

## create group
```
{
    method: "create_group_reply"
    status:
    description:
}
```

## get group
```
{
    method: "get_group"
    username:
}
```

## get group reply
```
{
    method: "get_group_reply"
    status:
    description:
    groups: [{group_name: , group_id: }, ...]
}
```

## add member to group
```
{
    method: "add_member_to_group"
    username_adder:
    username_to_add:
    group_id:
}
```

## add member to group reply
{
    method: "add_member_to_group_reply"
    status:
    description: 
}

## quit from group
```
{
    method: quit_from_group
    group_id:
}
```

## quit from group reply
```
{
    method: quit_from_group_reply
    status:
    description:
}
```

## send client
```
{
    method: send_client
    username_to:
    username_from:
    message:
}
```

## send client
```
{
    method: send_client_reply
    status:
    description:
}
```

## send group
```
{
    method: send_group
    username_from:
    group_id_to:
    message:
}
```

## send group reply
```
{
    method: send_group_reply
    status:
    description
}
```

