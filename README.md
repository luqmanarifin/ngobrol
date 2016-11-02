# ngobrol
A repository of cool messaging app, including server and client source code. Using RabbitMQ technology at server-side.

# message structure

## Register request
```
{
    method: "register",
    username:
    password:
    queue_name:
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
    queue_name
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
    username_to:
    token:
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
    token:
}
```

## get friend reply
```
{
    method: get_friend_reply
    status:
    description:
    usernames: list username
}
```

## create group
```
{
    method: "create_group"
    username:
    group_name:
    token:
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
    token:
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
    token:
}
```

## add member to group reply
```
{
    method: "add_member_to_group_reply"
    status:
    description:
}
```

## quit from group
```
{
    method: quit_from_group
    username:
    group_id:
    token:
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
    token:
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
    token:
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
