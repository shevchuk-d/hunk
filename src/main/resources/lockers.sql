
DO $fill_lockers$
BEGIN
   FOR i IN 1..100 LOOP
      INSERT INTO lockers(number) VALUES (i);
   END LOOP;
END $fill_lockers$;

DO $fill_lockers_neighbors$
BEGIN
   FOR i in 1..100 LOOP
      IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 1) THEN
         INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 1);
         IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 2) THEN
            INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 2);
            IF ( i % 2 != 0 ) THEN
               IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 3) THEN
                  INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 3);
               END IF;
            END IF;
         END IF;
      END IF;
      IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 1) THEN
         INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 1);
         IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 2) THEN
            INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 2);
            IF ( i % 2 = 0 ) THEN
               IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 3) THEN
                  INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 3);
               END IF;
            END IF;
         END IF;
      END IF;
   END LOOP;
END $fill_lockers_neighbors$;

DO $fill_clients$
BEGIN
  FOR i in 1..100 LOOP
    INSERT INTO clients(name, sex) VALUES (concat('client', i), 'smth');
  END LOOP;
END $fill_clients$;

DO $activate_visits$
BEGIN
  FOR i in 1..100 LOOP
    IF ( i % 5 =0 ) THEN
      INSERT INTO visits(start, finish, locker_id, client_id) VALUES (current_timestamp, NULL, i, i );
    END IF;
  END LOOP;
END $activate_visits$;

DO $finish_visits$
BEGIN
  FOR i in 1..100 LOOP
    IF ( i % 2 = 0 ) THEN
      UPDATE visits SET finish = current_timestamp + interval '2 hours' WHERE visit_id = i;
    END IF;
  END LOOP;
END $finish_visits$;

SELECT * FROM visits