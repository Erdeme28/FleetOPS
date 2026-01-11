import streamlit as st
import requests
import streamlit.components.v1 as components

API_URL = "http://gateway:8080"        # REST (docker network)
WS_URL = "http://localhost:8080/ws"    # WebSocket (browser)

st.set_page_config(page_title="FleetOPS", layout="centered")

st.title("🚗 FleetOPS")
st.caption("Cloud-native fleet management system")

if "token" not in st.session_state:
    st.session_state.token = None

with st.sidebar:
    st.header("🔐 Authentication")

    username = st.text_input("Username")
    password = st.text_input("Password", type="password")

    if st.button("Login"):
        try:
            r = requests.post(
                f"{API_URL}/api/auth/login",
                json={"username": username, "password": password},
                timeout=5
            )

            if r.ok:
                st.session_state.token = r.json()["token"]
                st.success("Successfully authenticated")
            else:
                st.error("Login failed")
        except Exception as e:
            st.error(str(e))

    if st.button("Logout"):
        st.session_state.token = None
        st.info("Logged out")

if not st.session_state.token:
    st.warning("Please log in.")
    st.stop()

headers = {
    "Authorization": f"Bearer {st.session_state.token}"
}
tab1, tab2, tab3 = st.tabs(["🚚 Vehicles", "📦 Orders", "➕ Create order"])

with tab1:
    st.subheader("Vehicle list (REST API)")
    r = requests.get(f"{API_URL}/api/vehicles", headers=headers)
    if r.ok:
        st.table(r.json())
    else:
        st.error("Error while picking up vehicles")
with tab2:
    st.subheader("Order list (REST API)")
    r = requests.get(f"{API_URL}/api/orders", headers=headers)
    if r.ok:
        st.table(r.json())
    else:
        st.error("Error while taking orders")
with tab3:
    st.subheader("Create new order")

    start = st.text_input("Start location")
    end = st.text_input("Destination location")

    if st.button("Submit the order"):
        r = requests.post(
            f"{API_URL}/api/orders",
            json={"startLocation": start, "endLocation": end},
            headers=headers
        )

        if r.ok:
            st.success("Order created successfully")
        else:
            st.error("Error creating order")

st.divider()
st.subheader("🗺️ Vehicles – LIVE Map (WebSocket)")

components.html(
    f"""
    <div id="map" style="height: 420px;"></div>

    <link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/leaflet@1.9.4/dist/leaflet.css"/>

    <script src="https://cdn.jsdelivr.net/npm/leaflet@1.9.4/dist/leaflet.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

    <script>
      const map = L.map('map').setView([45.6579, 25.6012], 12);

      L.tileLayer('https://{{s}}.tile.openstreetmap.org/{{z}}/{{x}}/{{y}}.png', {{
        maxZoom: 19
      }}).addTo(map);

      const markers = {{}};

      const socket = new SockJS("{WS_URL}");
      const stompClient = Stomp.over(socket);
      stompClient.debug = null;

      stompClient.connect({{}}, function () {{
        stompClient.subscribe("/topic/vehicles", function (msg) {{
          const vehicles = JSON.parse(msg.body);

          vehicles.forEach(v => {{
            if (!v.latitude || !v.longitude) return;

            const pos = [v.latitude, v.longitude];

            if (!markers[v.id]) {{
              markers[v.id] = L.marker(pos)
                .addTo(map)
                .bindPopup("🚚 " + v.licensePlate);
            }} else {{
              markers[v.id].setLatLng(pos);
            }}
          }});
        }});
      }});
    </script>
    """,
    height=460,
)
