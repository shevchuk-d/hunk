
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

DELETE
FROM lockers_neighbors
WHERE (locker_id in (49, 50) AND neighbor_id in (51, 52))
OR (locker_id in (51, 52) AND neighbor_id in (49, 50))
;

DO $fill_clients$
BEGIN
  FOR i in 1..100 LOOP
    INSERT INTO clients(name, sex) VALUES (concat('client', i), 'male');
  END LOOP;
END $fill_clients$;

DO $fill_clients$
BEGIN
  FOR i in 1..100 LOOP
    INSERT INTO clients(name, sex) VALUES (concat('clientess', i), 'female');
  END LOOP;
END $fill_clients$;
