### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('smath24asynchfacadeArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxsmathfacade', graph_attr=nodeattr):
          smathasynchfacade=Custom('smathasynchfacade','./qakicons/symActorSmall.png')
          actionexec=Custom('actionexec','./qakicons/symActorDynamicWithobj.png')
     facadesmathasynch=Custom('facadesmathasynch','./qakicons/server.png')
     smathasynchfacade >> Edge(color='magenta', style='dotted', decorate='true', label='<currentMsg &nbsp; >',  fontcolor='black') >> actionexec
     facadesmathasynch >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> smathasynchfacade
diag
