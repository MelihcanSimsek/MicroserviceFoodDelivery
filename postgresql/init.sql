DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'restaurant-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "restaurant-service"');
   END IF;

   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'order-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "order-service"');
   END IF;

   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'delivery-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "delivery-service"');
   END IF;

   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notification-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "notification-service"');
   END IF;

   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'role-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "role-service"');
   END IF;

   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'user-service') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "user-service"');
   END IF;

END
$$;
